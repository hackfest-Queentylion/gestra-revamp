package com.queentylion.gestra.data.ble


import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.queentylion.gestra.domain.ble.GloveReceiveManager
import com.queentylion.gestra.domain.ble.GloveResult
import com.queentylion.gestra.util.ConnectionState
import com.queentylion.gestra.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@SuppressLint("MissingPermission")
class GloveBleReceiveManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val context: Context
) : GloveReceiveManager {

    private val DEVICE_NAME = "Glove Flex Sensor"

    private val GLOVE_FLEX_SERVICE_UUID = "05f2f637-4809-4623-acbe-e2f2114ad9fe"

    private val P0_CHARACTERISTICS_UUID = "870542dd-02a1-45f7-89f4-f56bbd9dc31c"

    private var characteristicsUuidArray = arrayListOf(
        P0_CHARACTERISTICS_UUID,
    )

    override val data: MutableSharedFlow<Resource<GloveResult>> = MutableSharedFlow()

    override var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    override var flexResistenceArray: ArrayDeque<IntArray> = ArrayDeque(200)

    override var flexResistance by mutableStateOf(IntArray(11))

    override var initializingMessage by mutableStateOf<String?>(null)

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private var gatt: BluetoothGatt? = null

    private var isScanning = false

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val scanCallback = object : ScanCallback() {

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (result.device.name == DEVICE_NAME) {
                coroutineScope.launch {
                    data.emit(Resource.Loading(message = "Connecting to device..."))
                }
                if (isScanning) {
                    result.device.connectGatt(context, false, gattCallback)
                    isScanning = false
                    bleScanner.stopScan(this)
                    startCollectGloveData()
                }
            }
        }
    }

    private var currentConnectionAttempt = 1
    private var MAXIMUM_CONNECTION_ATTEMPTS = 5

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    coroutineScope.launch {
                        data.emit(Resource.Loading(message = "Discovering Services..."))
                    }
                    gatt.discoverServices()
                    this@GloveBleReceiveManager.gatt = gatt
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    coroutineScope.launch {
                        data.emit(
                            Resource.Success(
                                data = GloveResult(
                                    IntArray(11),
                                    ConnectionState.Disconnected
                                )
                            )
                        )
                    }
                    gatt.close()
                }
            } else {
                gatt.close()
                currentConnectionAttempt += 1
                coroutineScope.launch {
                    data.emit(
                        Resource.Loading(
                            message = "Attempting to connect $currentConnectionAttempt/$MAXIMUM_CONNECTION_ATTEMPTS"
                        )
                    )
                }
                if (currentConnectionAttempt <= MAXIMUM_CONNECTION_ATTEMPTS) {
                    startReceiving()
                } else {
                    coroutineScope.launch {
                        data.emit(Resource.Error(errorMessage = "Could not connect to ble device"))
                    }
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            with(gatt) {
                printGattTable()
                coroutineScope.launch {
                    data.emit(Resource.Loading(message = "Adjusting MTU space..."))
                }
                gatt.requestMtu(517)
            }
        }

        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
            enableNotification(gatt)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
        ) {
            val characteristicUuids = listOf(
                UUID.fromString(P0_CHARACTERISTICS_UUID),
            )

            val index = characteristicUuids.indexOf(characteristic.uuid)
            if (index != -1) {
                val arrayLen = value.size / 4;

                val resultArray = IntArray(arrayLen)

                for (i in 0 until arrayLen) {
                    // Calculate the starting index of each group of 4 bytes
                    val startIndex = i * 4
                    // Extract the four consecutive bytes and convert them to an integer
                    val intValue = value[startIndex].toInt() and 0xFF or
                            (value[startIndex + 1].toInt() and 0xFF shl 8) or
                            (value[startIndex + 2].toInt() and 0xFF shl 16) or
                            (value[startIndex + 3].toInt() shl 24)

                    // Store the integer value in the result array
                    resultArray[i] = intValue
                }

                val gloveResult = GloveResult(
                    // Replace with actual logic to create an IntArray from characteristic values.
                    resultArray,
                    ConnectionState.Connected
                )

                coroutineScope.launch {
                    data.emit(
                        Resource.Success(data = gloveResult)
                    )
                }
            }
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorWrite(gatt, descriptor, status)
            characteristicsUuidArray.removeAt(0)
            if (gatt != null) {
                enableNotification(gatt)
            }
        }


    }


    private fun enableNotification(gatt: BluetoothGatt) {
        val cccdUuid = UUID.fromString(CCCD_DESCRIPTOR_UUID)
        val payload = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE

        if (characteristicsUuidArray.size == 0) {
            characteristicsUuidArray = arrayListOf(
                P0_CHARACTERISTICS_UUID,
            )

            return
        };

        val characteristic = findCharacteristics(
            GLOVE_FLEX_SERVICE_UUID,
            characteristicsUuidArray[0]
        )

        if (characteristic != null) {
            characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
        };

        characteristic?.getDescriptor(cccdUuid)?.let { cccdDescriptor ->
            if (!gatt.setCharacteristicNotification(characteristic, true)) {
                Log.d("BLEReceiveManager", "set characteristics notification failed")
                return
            }
            writeDescription(cccdDescriptor, payload)
        }
    }

    private fun writeDescription(descriptor: BluetoothGattDescriptor, payload: ByteArray) {
        gatt?.let { gatt ->
            descriptor.value = payload
            gatt.writeDescriptor(descriptor)
        } ?: error("Not connected to a BLE device!")
    }

    private fun findCharacteristics(
        serviceUUID: String,
        characteristicsUUID: String
    ): BluetoothGattCharacteristic? {
        return gatt?.services?.find { service ->
            service.uuid.toString() == serviceUUID
        }?.characteristics?.find { characteristics ->
            characteristics.uuid.toString() == characteristicsUUID
        }
    }

    override fun startReceiving() {
        coroutineScope.launch {
            data.emit(Resource.Loading(message = "Scanning Ble devices..."))
        }
        isScanning = true
        bleScanner.startScan(null, scanSettings, scanCallback)
    }

    override fun reconnect() {
        gatt?.connect()
    }

    override fun disconnect() {
        gatt?.disconnect()
    }


    override fun closeConnection() {
        bleScanner.stopScan(scanCallback)

        // Assuming 'gatt' is the connected BluetoothGatt instance
        val serviceUuid = UUID.fromString(GLOVE_FLEX_SERVICE_UUID)
        val service = gatt?.getService(serviceUuid)

        if (service != null) {
            // List of characteristic UUIDs
            val characteristicsUuids = listOf(
                UUID.fromString(P0_CHARACTERISTICS_UUID),
            )

            // Iterate through each characteristic UUID and disable notifications
            for (charUuid in characteristicsUuids) {
                val characteristic = service.getCharacteristic(charUuid)
                if (characteristic != null) {
                    disconnectCharacteristic(characteristic)
                }
            }
        }

        gatt?.close()
    }

    private fun disconnectCharacteristic(characteristic: BluetoothGattCharacteristic) {
        val cccdUuid = UUID.fromString(CCCD_DESCRIPTOR_UUID)
        characteristic.getDescriptor(cccdUuid)?.let { cccdDescriptor ->
            if (gatt?.setCharacteristicNotification(characteristic, false) == false) {
                Log.d("GloveReceiveManager", "set characteristics notification failed")
                return
            }
            writeDescription(cccdDescriptor, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
        }
    }

    private fun startCollectGloveData() {
        coroutineScope.launch {
            data.collect { result ->
                delay(30)
                when (result) {
                    is Resource.Success -> {
                        connectionState = result.data.connectionState
                        val updatedFlexResistance = flexResistance.copyOf()
                        for ((index, value) in result.data.flexDegree.withIndex()) {
                            if (value != 0) {
                                updatedFlexResistance[index] = value
                            }
                        }
                        flexResistance = updatedFlexResistance
                        try {
                            if (flexResistenceArray.size >= 1000) {
                                flexResistenceArray.removeLast()
                                flexResistenceArray.addFirst(flexResistance)
                            } else {
                                flexResistenceArray.addFirst(flexResistance)
                            }
                            Log.d("BABI", flexResistenceArray.first[0].toString())
                        } catch (error: Throwable) {
                            // Handle error
                            error.printStackTrace()
                        }
                    }

                    is Resource.Loading -> {
                        Log.d("MEMEKBESAR", "LOADING")
                        initializingMessage = result.message
                        connectionState =
                            result.data?.connectionState ?: ConnectionState.CurrentlyInitializing
                    }

                    is Resource.Error -> {
                        Log.d("MEMEKBESAR", "ERROR")
                        connectionState = ConnectionState.Uninitialized
                    }
                }
//                if (connectionState === ConnectionState.Uninitialized) {
//                    startReceiving()
//                } else if (connectionState === ConnectionState.Disconnected) {
//                    reconnect()
//                }
            }
        }
    }

}