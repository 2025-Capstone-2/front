package com.example.frontcapstone2025.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontcapstone2025.components.layout.BottomMenu
import com.example.frontcapstone2025.components.layout.MainPageTopBar
import com.example.frontcapstone2025.ui.theme.DivideLineColor
import com.example.frontcapstone2025.ui.theme.TextColorGray
import com.example.frontcapstone2025.viemodel.MainViewModel

@SuppressLint("DefaultLocale")
@Composable
fun SettingPage(
    bottomBaronClickedActions: List<() -> Unit>,
    navToHelpPage: () -> Unit,
    mainViewModel: MainViewModel
) {
    var locationPermission by rememberSaveable { mutableStateOf(true) }

    val chosenWifi by mainViewModel.chosenWifi.collectAsState()
    Scaffold(
        topBar = {
            MainPageTopBar(
                pinnedWifiName = chosenWifi,
                navToHelpPage = navToHelpPage
            )
        },
        bottomBar = {
            BottomMenu(
                bottomBaronClickedActions = bottomBaronClickedActions,
                currentScreen = "SettingPage"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                // 제목
                Text(
                    text = "권한 관리",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
                    color = TextColorGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    thickness = 3.dp,
                    color = DivideLineColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 설명 텍스트
                Text(
                    buildAnnotatedString {
                        append("아래 권한을 허용하지 않으면 ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("앱의 기능을 사용할 수 없습니다.")
                        }
                        append(" 현재 허용된 권한 목록은 아래와 같습니다.")
                    },
                    color = TextColorGray,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 위치 권한 스위치
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
//                        Switch(
//                            checked = locationPermission,
//                            onCheckedChange = { locationPermission = it },
//                            colors = SwitchDefaults.colors(
//                                checkedThumbColor = BottomBarBackground,
//                                checkedTrackColor = DivideLineColor,
//                                disabledCheckedThumbColor = BottomBarBackground, // 진한 Thumb
//                                disabledCheckedTrackColor = BottomBarBackground.copy(alpha = 0.4f) // 흐려진 트랙
//                            ),
//                            enabled = false
//                        )
                        Checkbox(
                            checked = locationPermission,
                            onCheckedChange = { locationPermission = it },
                            colors = CheckboxDefaults.colors(
//                                checkedColor = Color.Green,
//                                uncheckedColor = Color.Gray,
//                                checkmarkColor = Color.White,
//                                disabledCheckedColor = Color.LightGray,
//                                disabledUncheckedColor = Color.DarkGray
                            ),
                            enabled = false,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "위치 권한",
                            color = TextColorGray,
                            modifier = Modifier.height(28.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
