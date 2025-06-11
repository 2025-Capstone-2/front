package com.example.frontcapstone2025.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewModelScope
import com.example.frontcapstone2025.R
import com.example.frontcapstone2025.components.buttons.CustomButton
import com.example.frontcapstone2025.components.layout.TopBarWithBack
import com.example.frontcapstone2025.ui.theme.TextColorGray
import com.example.frontcapstone2025.viemodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun GetAllDistancePage(
    navigationBack: () -> Unit,
    navToOneDistancePage: List<() -> Unit>,
    navToHelpPage: () -> Unit,
    mainViewModel: MainViewModel,
    navToHome: () -> Unit
) {
    val chosenWifi by mainViewModel.chosenWifi.collectAsState()
    val originDistance by mainViewModel.originDistance.collectAsState()
    val originRightDistance by mainViewModel.originRightDistance.collectAsState()
    val originCrossOneDistance by mainViewModel.originCrossOneDistance.collectAsState()
    val originCrossTwoDistance by mainViewModel.originCrossTwoDistance.collectAsState()
    val oneSideLength by mainViewModel.oneSideLength.collectAsState()
    val kneeToEyesLength by mainViewModel.kneeToEyesLength.collectAsState()


    val upImage = painterResource(R.drawable.up)
    val downImage = painterResource(R.drawable.down)
    val leftImage = painterResource(R.drawable.left)
    val frontImage = painterResource(R.drawable.front)

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
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
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
                        text = "※ 주의",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = TextColorGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("1. 최초 측정지는")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("오른쪽과 오른쪽 앞 공간이 충분한 지점")
                            }
                            append("에서 시작하세요.")
                        },
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = TextColorGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("2. 핸드폰으로 측정할 때,")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("1번과 4번 지점에서는 눈높이")
                            }
                            append("에서 측정하고, ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("나머지(2,3) 지점에서는 무릎 높이")
                            }
                            append("에 핸드폰을 두고 측정하세요.")
                        },
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = TextColorGray
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.5f)
                ) {
                    Image(
                        painter = upImage,
                        contentDescription = null,
                        modifier = Modifier
                            .height(196.dp)
                            .clickable { navToOneDistancePage[0]() }
                    )
                    Text(
                        text = if (originDistance != -1.0) "1번 위치 측정: %.4f m".format(originDistance) else "1번 위치 측정: X",
                        modifier = Modifier,
                        color = TextColorGray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.5f)
                ) {
                    Image(
                        painter = downImage,
                        contentDescription = null,
                        modifier = Modifier
                            .height(196.dp)
                            .clickable { navToOneDistancePage[1]() }
                    )
                    Text(
                        text = if (originRightDistance != -1.0) "2번 위치 측정: %.4f m".format(
                            originRightDistance
                        ) else "2번 위치 측정: X",
                        modifier = Modifier,
                        color = TextColorGray

                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.5f)
                ) {
                    Image(
                        painter = leftImage,
                        contentDescription = null,
                        modifier = Modifier
                            .height(196.dp)
                            .clickable { navToOneDistancePage[2]() }
                    )
                    Text(
                        text = if (originCrossOneDistance != -1.0) "3번 위치 측정: %.4f m".format(
                            originCrossOneDistance
                        ) else "3번 위치 측정: X",
                        modifier = Modifier,
                        color = TextColorGray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.5f)
                ) {
                    Image(
                        painter = frontImage,
                        contentDescription = null,
                        modifier = Modifier
                            .height(196.dp)
                            .clickable { navToOneDistancePage[3]() }
                    )
                    Text(
                        text = if (originCrossTwoDistance != -1.0) "4번 위치 측정: %.4f m".format(
                            originCrossTwoDistance
                        ) else "4번 위치 측정: X",
                        modifier = Modifier,
                        color = TextColorGray

                    )
                }
            }

            CustomButton(
                text = "거리 측정 완료",
                onClicked = {
                    mainViewModel.viewModelScope.launch {
                        mainViewModel.getWifiPosition()
                        mainViewModel.setShowDialog(true)
                        navToHome()
                    }
                },
                enabled = (
                        originDistance != -1.0 &&
                                originRightDistance != -1.0 &&
                                originCrossOneDistance != -1.0 &&
                                originCrossTwoDistance != -1.0 &&
                                oneSideLength != -1.0 &&
                                kneeToEyesLength != -1.0
                        ),
            )
        }


    }
}