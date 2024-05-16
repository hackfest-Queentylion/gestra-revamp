package com.queentylion.gestra.ui.screens.settings

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.queentylion.gestra.domain.ble.GloveReceiveManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class SettingsState(
    val bluetoothEnabled: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val gloveReceiveManager: GloveReceiveManager,
) : ViewModel() {
    companion object {
        const val REQUEST_ENABLE_BLUETOOTH = 1
        const val REQUEST_BLUETOOTH_PERMISSION = 2
    }

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    private var bluetoothManager: BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null

    fun initializeBluetooth(context: Context) {
        bluetoothManager = ContextCompat.getSystemService(context, BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun requestBluetoothPermissionsAndEnable(
        activity: ComponentActivity,
    ) {
        // Define the permissions you need
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        // Check if permissions are granted
        val permissionsToRequest = permissions.filter {
            ActivityCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }

        // Request permissions if necessary
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                permissionsToRequest.toTypedArray(),
                REQUEST_BLUETOOTH_PERMISSION,
            )
            enableBluetooth(activity)
        } else {
            // If permissions are already granted, enable Bluetooth
            enableBluetooth(activity)
        }
//        gloveReceiveManager.startReceiving()
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun enableBluetooth(activity: ComponentActivity) {
        if (!isBluetoothEnabled()) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }
    }

    fun initializeGloveConnection() {
        gloveReceiveManager.startReceiving()
    }

//    @RequiresApi(Build.VERSION_CODES.S)
//    fun requestEnableBluetooth(
//        activity: ComponentActivity,
//    ) {
//        if (!isBluetoothEnabled()) {
//            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//
//            val permissions = arrayOf(
//                Manifest.permission.BLUETOOTH_CONNECT,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//
//            val permissionsToRequest = permissions.filter {
//                ActivityCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
//            }
//
//
//            if (permissionsToRequest.isNotEmpty()) {
//                ActivityCompat.requestPermissions(
//                    activity,
//                    permissionsToRequest.toTypedArray(),
//                    REQUEST_BLUETOOTH_PERMISSION
//                )
//                activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
//            } else {
//                // If permissions are already granted, enable Bluetooth
////                enableBluetooth(activity)
//                activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
//            }

//            if (ActivityCompat.checkSelfPermission(
//                    activity.baseContext,
//                    Manifest.permission.BLUETOOTH_CONNECT
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    activity,
//                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
//                    REQUEST_BLUETOOTH_PERMISSION
//                )
//                activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
//            } else {
//                activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
//
//            }
//        }
//    }

//    fun requestBluetoothPermission(activity: Activity) {
//        ActivityCompat.requestPermissions(
//            activity,
//            arrayOf(Manifest.permission.BLUETOOTH),
//            REQUEST_BLUETOOTH_PERMISSION
//        )
//    }

//    @SuppressLint("MissingPermission")
//    fun requestBluetoothEnable(activity: Activity) {
//        Log.d("MMEK", bluetoothAdapter.isEnabled.toString())
//        if (!bluetoothAdapter.isEnabled) {
//            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
//        }
//    }


//    @RequiresApi(Build.VERSION_CODES.S)
//    fun checkBluetoothPermission(activity: Activity) {
//        val bluetoothPermissionGranted = ActivityCompat.checkSelfPermission(
//            activity,
//            android.Manifest.permission.BLUETOOTH
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val bluetoothScanPermissionGranted = ActivityCompat.checkSelfPermission(
//            activity,
//            android.Manifest.permission.BLUETOOTH_SCAN
//        ) == PackageManager.PERMISSION_GRANTED
//
//        _bluetoothPermissionState.value =
//            bluetoothPermissionGranted && bluetoothScanPermissionGranted
//
//        if (!bluetoothPermissionGranted || !bluetoothScanPermissionGranted) {
//            ActivityCompat.requestPermissions(
//                activity,
//                arrayOf(
//                    Manifest.permission.BLUETOOTH_SCAN,
//                    Manifest.permission.BLUETOOTH_CONNECT,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ),
//                REQUEST_BLUETOOTH_PERMISSION
//            )
//        }
//    }

//    init {
//        viewModelScope.launch {
//            bluetoothEnabledState.observeForever { enabled ->
//                if (enabled) {
//                    if (_bluetoothEnabledState.value == true && _bluetoothPermissionState.value == true) {
//                        gloveReceiveManager.startReceiving()
//                    }
//                }
//            }
//        }
//    }
}
