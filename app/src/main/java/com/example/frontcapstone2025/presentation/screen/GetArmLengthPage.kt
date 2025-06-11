package com.example.frontcapstone2025.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.frontcapstone2025.components.buttons.CustomButton
import com.example.frontcapstone2025.components.layout.TopBarWithBack
import com.example.frontcapstone2025.ui.theme.DivideLineColor
import com.example.frontcapstone2025.ui.theme.TextColorGray
import com.example.frontcapstone2025.viemodel.MainViewModel

@Composable
fun GetArmLengthPage(
    navigationBack: () -> Unit,
    moveToGetAllDistancePage: () -> Unit,
    navToHelpPage: () -> Unit,
    mainViewModel: MainViewModel,
) {
    val oneSideLength by mainViewModel.oneSideLength.collectAsState()
    val kneeToEyesLength by mainViewModel.kneeToEyesLength.collectAsState()
    val chosenWifi by mainViewModel.chosenWifi.collectAsState()

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
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 단위 길이
            Text(
                text = "측정할 단위 길이를 cm로 입력하세요. 1m 이상이 적당합니다.\n",
                modifier = Modifier.padding(bottom = 8.dp),
                color = TextColorGray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = if (oneSideLength == -1.0) "" else oneSideLength.toString(),
                    onValueChange = { ar ->
                        if (ar.matches(Regex("^\\d{0,3}(\\.\\d{0,2})?$"))) {
                            ar.toDoubleOrNull()?.let {
                                mainViewModel.setOneLength(it)
                            }
                        }
                    },
                    placeholder = { Text("cm 단위로 입력 -> ex) 100.0") },
                    singleLine = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = {
//                            moveToGetAllDistancePage()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = TextColorGray,
                        unfocusedTextColor = TextColorGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = DivideLineColor,
                        disabledPlaceholderColor = TextColorGray,
                        focusedPlaceholderColor = TextColorGray,
                        unfocusedPlaceholderColor = TextColorGray,
                        disabledIndicatorColor = DivideLineColor,
                        unfocusedIndicatorColor = DivideLineColor,
                        cursorColor = DivideLineColor
                    )

                )
                Text(
                    text = "cm",
                    modifier = Modifier.padding(start = 8.dp),
                    color = TextColorGray
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            // 무릎부터 눈까지 길이
            Text(
                text = "무릎부터 눈 까지의 길이를 측정하여 cm로 입력하세요.\n",
                modifier = Modifier.padding(bottom = 8.dp),
                color = TextColorGray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = if (kneeToEyesLength == -1.0) "" else kneeToEyesLength.toString(),
                    onValueChange = { ar ->
                        if (ar.matches(Regex("^\\d{0,3}(\\.\\d{0,2})?$"))) {
                            ar.toDoubleOrNull()?.let {
                                mainViewModel.setKneeToEyesLength(it)
                            }
                        }
                    },
                    placeholder = { Text("cm 단위로 입력 -> ex) 100.0") },
                    singleLine = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = {
//                            moveToGetAllDistancePage()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = TextColorGray,
                        unfocusedTextColor = TextColorGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = DivideLineColor,
                        disabledPlaceholderColor = TextColorGray,
                        focusedPlaceholderColor = TextColorGray,
                        unfocusedPlaceholderColor = TextColorGray,
                        disabledIndicatorColor = DivideLineColor,
                        unfocusedIndicatorColor = DivideLineColor,
                        cursorColor = DivideLineColor
                    )

                )
                Text(
                    text = "cm",
                    modifier = Modifier.padding(start = 8.dp),
                    color = TextColorGray
                )
            }

            CustomButton(
                onClicked = moveToGetAllDistancePage,
                text = "거리 측정 시작",
                enabled = (oneSideLength != -1.0 && kneeToEyesLength != -1.0),
            )

        }
    }
}
