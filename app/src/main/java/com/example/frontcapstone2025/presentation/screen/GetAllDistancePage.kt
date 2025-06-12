package com.example.frontcapstone2025.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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


    val toiletPlan = painterResource(R.drawable.toilet_plan)

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
//                .padding(horizontal = 16.dp)
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
                            append("1. 최초 측정지(1번 위치)는 ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("동쪽과 북동쪽의 공간이 충분한 지점")
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
                            append("2. 도면과 같은 측정 위치에 서서 사람을 눌러 측정 페이지로 들어가세요.")
                        },
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = TextColorGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("3. 각각의 위치는 앞에서 입력한 ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("단위 거리만큼 ")
                            }
                            append("떨어져 있습니다.\n ex) 2번 위치는 1번 위치(최초 위치)로부터 동쪽(오른쪽)으로 단위 거리만큼 떨어져 있습니다.")
                        },
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = TextColorGray
                    )
                }

            }

            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Image(
                    painter = toiletPlan,
                    contentDescription = null,
                )

                Button(
                    onClick = { navToOneDistancePage[0]() },
                    modifier = Modifier
                        .offset(x = 150.dp, y = 313.dp)
                        .size(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                ) {
                    Text("")
                }

                Button(
                    onClick = { navToOneDistancePage[1]() },
                    modifier = Modifier
                        .offset(x = 223.dp, y = 313.dp)
                        .size(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                ) {
                    Text("")
                }

                Button(
                    onClick = { navToOneDistancePage[2]() },
                    modifier = Modifier
                        .offset(x = 223.dp, y = 242.dp)
                        .size(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                ) {
                    Text("")
                }

                Button(
                    onClick = { navToOneDistancePage[3]() },
                    modifier = Modifier
                        .offset(x = 223.dp, y = 163.dp)
                        .size(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                ) {
                    Text("")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (originDistance != -1.0) "1번 위치 측정: %.2f m".format(originDistance) else "1번 위치 측정: X",
                    modifier = Modifier.weight(1f),
                    color = TextColorGray
                )
                Text(
                    text = if (originRightDistance != -1.0) "2번 위치 측정: %.2f m".format(
                        originRightDistance
                    ) else "2번 위치 측정: X",
                    modifier = Modifier.weight(1f),
                    color = TextColorGray
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (originCrossOneDistance != -1.0) "3번 위치 측정: %.2f m".format(
                        originCrossOneDistance
                    ) else "3번 위치 측정: X",
                    modifier = Modifier.weight(1f),
                    color = TextColorGray
                )
                Text(
                    text = if (originCrossTwoDistance != -1.0) "4번 위치 측정: %.2f m".format(
                        originCrossTwoDistance
                    ) else "4번 위치 측정: X",
                    modifier = Modifier.weight(1f),
                    color = TextColorGray
                )
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


//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 32.dp)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.weight(0.5f)
//                ) {
//                    Image(
//                        painter = upImage,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .height(196.dp)
//                            .clickable { navToOneDistancePage[0]() }
//                    )
//                    Text(
//                        text = if (originDistance != -1.0) "1번 위치 측정: %.4f m".format(originDistance) else "1번 위치 측정: X",
//                        modifier = Modifier,
//                        color = TextColorGray
//                    )
//                }
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.weight(0.5f)
//                ) {
//                    Image(
//                        painter = downImage,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .height(196.dp)
//                            .clickable { navToOneDistancePage[1]() }
//                    )
//                    Text(
//                        text = if (originRightDistance != -1.0) "2번 위치 측정: %.4f m".format(
//                            originRightDistance
//                        ) else "2번 위치 측정: X",
//                        modifier = Modifier,
//                        color = TextColorGray
//
//                    )
//                }
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.weight(0.5f)
//                ) {
//                    Image(
//                        painter = leftImage,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .height(196.dp)
//                            .clickable { navToOneDistancePage[2]() }
//                    )
//                    Text(
//                        text = if (originCrossOneDistance != -1.0) "3번 위치 측정: %.4f m".format(
//                            originCrossOneDistance
//                        ) else "3번 위치 측정: X",
//                        modifier = Modifier,
//                        color = TextColorGray
//                    )
//                }
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.weight(0.5f)
//                ) {
//                    Image(
//                        painter = frontImage,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .height(196.dp)
//                            .clickable { navToOneDistancePage[3]() }
//                    )
//                    Text(
//                        text = if (originCrossTwoDistance != -1.0) "4번 위치 측정: %.4f m".format(
//                            originCrossTwoDistance
//                        ) else "4번 위치 측정: X",
//                        modifier = Modifier,
//                        color = TextColorGray
//
//                    )
//                }
//            }

    }


}
