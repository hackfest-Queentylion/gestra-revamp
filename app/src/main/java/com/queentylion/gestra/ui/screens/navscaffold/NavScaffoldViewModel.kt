package com.queentylion.gestra.ui.screens.navscaffold

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.queentylion.gestra.domain.ble.GloveReceiveManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigationScaffoldState(
    val currentDestinationLabel: String = "Home"
)

@HiltViewModel
class NavScaffoldViewModel @Inject constructor(
//    val gloveReceiveManager: GloveReceiveManager,
//    private val bluetoothAdapter: BluetoothAdapter
) : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationScaffoldState())
    val uiState: StateFlow<NavigationScaffoldState> = _uiState.asStateFlow()


    fun setNavigationScreen(label: String) {
        _uiState.value = NavigationScaffoldState(label)
    }

//    private val _bluetoothEnabledState = MutableLiveData<Boolean>()
//    val bluetoothEnabledState: LiveData<Boolean> = _bluetoothEnabledState
//
//    private val _bluetoothPermissionState = MutableLiveData<Boolean>()
//    val bluetoothPermissionState: LiveData<Boolean> = _bluetoothPermissionState
//
//    fun checkBluetoothEnabled() {
//        _bluetoothEnabledState.value = bluetoothAdapter.isEnabled
//    }
//
//    fun requestBluetoothPermission(activity: Activity) {
//        ActivityCompat.requestPermissions(
//            activity,
//            arrayOf(Manifest.permission.BLUETOOTH),
//            REQUEST_BLUETOOTH_PERMISSION
//        )
//    }
//
//    @SuppressLint("MissingPermission")
//    fun requestBluetoothEnable(activity: Activity) {
//        Log.d("MMEK", bluetoothAdapter.isEnabled.toString())
//        if (!bluetoothAdapter.isEnabled) {
//            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
//        }
//    }
//
//    companion object {
//        const val REQUEST_ENABLE_BLUETOOTH = 1
//        const val REQUEST_BLUETOOTH_PERMISSION = 2
//    }
//
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
