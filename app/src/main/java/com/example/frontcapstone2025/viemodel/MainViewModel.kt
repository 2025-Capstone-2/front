package com.example.frontcapstone2025.viemodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontcapstone2025.api.RetrofitManager
import com.example.frontcapstone2025.api.WifiPosition
import com.example.frontcapstone2025.utility.WifiUkf
import com.example.frontcapstone2025.utility.toDisplayList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel : ViewModel() {
    private val _chosenWifi = MutableStateFlow("")
    val chosenWifi: StateFlow<String> = _chosenWifi.asStateFlow()

    private val _originDistance = MutableStateFlow(-1.0)
    val originDistance: StateFlow<Double> = _originDistance.asStateFlow()
    private val _originRightDistance = MutableStateFlow(-1.0)
    val originRightDistance: StateFlow<Double> = _originRightDistance.asStateFlow()
    private val _originCrossOneDistance = MutableStateFlow(-1.0)
    val originCrossOneDistance: StateFlow<Double> = _originCrossOneDistance.asStateFlow()
    private val _originCrossTwoDistance = MutableStateFlow(-1.0)
    val originCrossTwoDistance: StateFlow<Double> = _originCrossTwoDistance.asStateFlow()
    private val _oneSideLength = MutableStateFlow(-1.0)
    val oneSideLength: StateFlow<Double> = _oneSideLength.asStateFlow()
    private val _kneeToEyesLength = MutableStateFlow(-1.0)
    val kneeToEyesLength: StateFlow<Double> = _kneeToEyesLength.asStateFlow()
    private val _scanTime = MutableStateFlow(-1)
    val scanTime: StateFlow<Int> = _scanTime.asStateFlow()

    private val _wifiPosition = MutableStateFlow(WifiPosition())
    val wifiPosition: StateFlow<WifiPosition> = _wifiPosition.asStateFlow()

    private val _wifiScanDelay = MutableStateFlow(1_000L)     // 와이파이 스캔 딜레이
    val wifiScanDelay: StateFlow<Long> = _wifiScanDelay.asStateFlow()

    private val _initialLoadingTime = MutableStateFlow(35_000L) // 로딩 딜레이
    val initialLoadingTime: StateFlow<Long> = _initialLoadingTime.asStateFlow()

    private val _wifiListReady = MutableStateFlow(true)
    val wifiListReady: StateFlow<Boolean> = _wifiListReady

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _suspiciousWifi = MutableStateFlow<List<String>>(emptyList())
    val suspiciousWifi: StateFlow<List<String>> = _suspiciousWifi.asStateFlow()


    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    suspend fun getWifiPosition() {
        // log all distance.value
        Log.d(
            "distance",
            "originDistance: ${originDistance.value} \noriginRightDistance: ${originRightDistance.value} \noriginCrossOneDistance: ${originCrossOneDistance.value} \noriginCrossTwoDistance: ${originCrossTwoDistance.value} \noneSideLength: ${oneSideLength.value} \n" +
                    "kneeToEyesLength: ${kneeToEyesLength.value}"
        )

        RetrofitManager.instance.getWifiPosition(
            origin = originDistance.value / 100,
            origin_right = originRightDistance.value / 100,
            origin_cross_one = originCrossOneDistance.value / 100,
            origin_cross_two = originCrossTwoDistance.value / 100,
            one_side_length = oneSideLength.value,
            knee_to_eyes = kneeToEyesLength.value,
            onSuccess = { wifiPosition: WifiPosition ->
                Log.d("wifiposition", wifiPosition.toString())
                _wifiPosition.update { wifiPosition }
            },
            onFailure = {

            }
        )
    }

    // MainViewModel.kt
    fun setChosenWifi(name: String) {
        _chosenWifi.value = name
    }

    fun getDistanceById(id: Int): StateFlow<Double> {
        return when (id) {
            1 -> originDistance
            2 -> originRightDistance
            3 -> originCrossOneDistance
            4 -> originCrossTwoDistance
            else -> MutableStateFlow(-1.0) // 혹은 throw IllegalArgumentException
        }
    }

    fun setWifiListReady(value: Boolean) {
        _wifiListReady.value = value
    }

    fun setOneLength(value: Double) {
        _oneSideLength.value = value
    }

    fun setKneeToEyesLength(value: Double) {
        _kneeToEyesLength.value = value
    }

    fun setScanTime(value: Int) {
        _scanTime.value = value
    }

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    fun setShowLoading(value: Boolean) {
        _showLoading.value = value
    }

    fun setDistanceById(id: Int, value: Double) {
        when (id) {
            1 -> _originDistance.value = value
            2 -> _originRightDistance.value = value
            3 -> _originCrossOneDistance.value = value
            4 -> _originCrossTwoDistance.value = value
            else -> {
                // 유효하지 않은 ID일 경우 로그 출력
                Log.w("MainViewModel", "Invalid ID passed to setDistanceById: $id")
            }
        }
    }


    // 버튼 클릭 시 쓰기 위해 일단 옮겨봄.
    suspend fun scanDistanceRepeatedly(
        id: Int,
        targetSsid: String,
        context: Context
    ) {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = true

        val ukfMap = mutableMapOf<String, WifiUkf>()

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                val results = wifiManager.scanResults
                val displays = results.toDisplayList(ukfMap)
                val match = displays.find { it.ssid == targetSsid }
                match?.let { setDistanceById(id, it.distance) }
            }
        }

        context.registerReceiver(
            receiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )

        val start = System.currentTimeMillis()
        val duration = _initialLoadingTime.value
        val delayTime = _wifiScanDelay.value
        try {
            while (System.currentTimeMillis() - start < duration) {
                try {
                    wifiManager.startScan()
                } catch (_: Exception) {
                }
                delay(delayTime)
            }
        } finally {
            try {
                context.unregisterReceiver(receiver)
            } catch (_: Exception) {
            }
        }
    }

    fun startCaptureAndAnalyze(context: Context) {
        viewModelScope.launch {
            _wifiListReady.value = false
            try {
                val startIntent = Intent(Intent.ACTION_VIEW).apply {
                    setClassName("com.usbwifimon.app", "com.usbwifimon.app.CaptureCtrl")
                    putExtra("action", "start")
                    putExtra("channel", -1)
                    putExtra("channel_width", 1)
                    putExtra("pcap_name", "Capture.pcap")
                }
                context.startActivity(startIntent)
                delay(30_000L)
                val stopIntent = Intent(Intent.ACTION_VIEW).apply {
                    setClassName("com.usbwifimon.app", "com.usbwifimon.app.CaptureCtrl")
                    putExtra("action", "stop")
                }
                context.startActivity(stopIntent)
                delay(5_000L)
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, "UsbWifiMonitor/Capture.pcap")
                val uri = Uri.fromFile(file)
                RetrofitManager.instance.analyzePcap(
                    context = context,
                    uri = uri,
                    onSuccess = { list ->
                        _suspiciousWifi.value = list
                        Log.d("startCapture", "onSuccess ${_showLoading.value}")
                    },
                    onFailure = {
                    }
                )
            } catch (e: Exception) {
                Log.e("startCapture", e.toString())
            } finally {
                _wifiListReady.value = true
                _showLoading.value = false
                Log.d("startCapture", "finally ${_showLoading.value}")
            }
        }
    }

    fun analyzePcap(context: Context, uri: Uri) {
        viewModelScope.launch {
            RetrofitManager.instance.analyzePcap(
                context = context,
                uri = uri,
                onSuccess = { list ->
                    _suspiciousWifi.value = list
                    _showLoading.value = false
                },
                onFailure = {}
            )
        }
    }
}