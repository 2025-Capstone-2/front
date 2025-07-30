package com.example.frontcapstone2025.presentation.screen

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontcapstone2025.components.buttons.CustomButton
import com.example.frontcapstone2025.components.items.LoadingComponent
import com.example.frontcapstone2025.components.layout.TopBarWithBack
import com.example.frontcapstone2025.ui.theme.TextColorGray
import com.example.frontcapstone2025.utility.rememberWifiDistances
import com.example.frontcapstone2025.viemodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun GetOneDistancePage(
    id: Int,
    navigationBack: () -> Unit,
    @DrawableRes imageResId: Int,
    navToHelpPage: () -> Unit,
    mainViewModel: MainViewModel
) {
    val chosenWifi by mainViewModel.chosenWifi.collectAsState()
    val distance by mainViewModel.getDistanceById(id).collectAsState()

    val wifiScanDelay by mainViewModel.wifiScanDelay.collectAsState()

    val scanTime by mainViewModel.scanTime.collectAsState()
    Log.d("measuring outside", "$scanTime, ${scanTime.toLong()}")

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var measuring by remember { mutableStateOf(false) }
    var measureKey by remember { mutableStateOf(0) }
    val collectedDistances = remember { mutableStateListOf<Double>() }
    val wifiDistances by rememberWifiDistances(measuring, wifiScanDelay, measureKey)

    LaunchedEffect(wifiDistances, measuring) {
        if (measuring) {
            wifiDistances.find { it.ssid == chosenWifi }?.distance?.let {
                collectedDistances.add(it)
            }
        }
    }

    LaunchedEffect(measureKey) {
        if (measuring) {
            Log.d("measuring", "$scanTime, ${scanTime.toLong()}")
            delay(scanTime.toLong() * 1_000L)
            val avg = if (collectedDistances.isNotEmpty()) collectedDistances.average() else -1.0
            mainViewModel.setDistanceById(id, avg)
            collectedDistances.clear()
            measuring = false
            isLoading = false
        }
    }

    if (isLoading) {
        LoadingComponent(text = "수상한 기기와의 거리를 측정하고 있어요.") // 로딩 중일 때 표시
    } else {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopBarWithBack(
                    navigationBack = navigationBack,
                    pinnedWifiName = chosenWifi,
                    navToHelpPage = navToHelpPage
                )
            },

            ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFB4C5AB))
                        .padding(16.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Column {
                        Text(
                            text = "※ 안내 사항",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            color = TextColorGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = buildAnnotatedString {
                                append("1. 사진과 같이 핸드폰을 ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(if (id == 1 || id == 4) "머리 위" else "배꼽 높이")
                                }
                                append("로 들고, 측정 시작 버튼을 눌러주세요.")
                            },
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            color = TextColorGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = buildAnnotatedString {
                                append("2. 측정에는 대략 ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("30초")
                                }
                                append("정도가 걸립니다. 측정하는 동안 핸드폰의 ")
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("움직임을 최소화")
                                }
                                append("하세요.")
                            },
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            color = TextColorGray
                        )
                    }

                }
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .height(496.dp)
                        .padding(top = 16.dp)
                        .padding(8.dp)
                )
                Text(
                    text = if (distance != -1.0) "${id}번 위치 거리 : $distance" else "거리 : ",
                    color = TextColorGray,
                )
                CustomButton(
                    text = "측정 시작하기",
                    onClicked = {
                        mainViewModel.setDistanceById(id, -1.0)
                        collectedDistances.clear()
                        isLoading = true
                        measuring = true
                        measureKey++
                    }
                )

            }
        }
    }
}