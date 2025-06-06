package com.example.frontcapstone2025.presentation.navigation

import HelpPage
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontcapstone2025.R
import com.example.frontcapstone2025.presentation.screen.GetAllDistancePage
import com.example.frontcapstone2025.presentation.screen.GetArmLengthPage
import com.example.frontcapstone2025.presentation.screen.GetOneDistancePage
import com.example.frontcapstone2025.presentation.screen.MainPage
import com.example.frontcapstone2025.presentation.screen.SearchWifiPage
import com.example.frontcapstone2025.presentation.screen.SettingPage
import com.example.frontcapstone2025.presentation.screen.WifiListPage
import com.example.frontcapstone2025.viemodel.MainViewModel

fun navivationWithClear(navController: NavController, route: String) {
    navController.popBackStack()
    navController.navigate(route)
}

@Composable
fun Navigator(
    mainViewModel: MainViewModel,
) {
    val navController = rememberNavController()

    val navigationBack: () -> Unit = { navController.navigateUp() }

    var centerButtonTarget by rememberSaveable { mutableStateOf("MainPage") }
    val bottomBaronClickedActions = listOf(
        { navivationWithClear(navController = navController, route = "SearchWifiPage") },
        {
            navivationWithClear(navController, centerButtonTarget) // 상태 기반으로 이동
        },
        { navivationWithClear(navController = navController, route = "SettingPage") },
    )


    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "MainPage",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "MainPage") {
                MainPage(
                    bottomBaronClickedActions = bottomBaronClickedActions,
                    moveToSearchWifiListPage = {
                        navController.navigate("WifiListPage")
                        centerButtonTarget = "WifiListPage"
                    },
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel

                )
            }
            composable(route = "SettingPage") {
                SettingPage(
                    bottomBaronClickedActions = bottomBaronClickedActions,
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel
                )
            }
            composable(route = "SearchWifiPage") {
                SearchWifiPage(
                    bottomBaronClickedActions = bottomBaronClickedActions,
                    moveToGetArmLengthPage = { navController.navigate("GetArmLengthPage") },
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel
                )
            }

            composable(route = "WifiListPage") {
                WifiListPage(
                    bottomBaronClickedActions = bottomBaronClickedActions,
                    moveToFirstMainPage = {
                        centerButtonTarget = "MainPage"
                        navController.navigate("MainPage")
                    },
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel
                )
            }

            composable(route = "GetArmLengthPage") {
                GetArmLengthPage(
                    navigationBack = navigationBack,
                    moveToGetAllDistancePage = { navController.navigate("GetAllDistancePage") },
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel
                )
            }
            composable(route = "GetAllDistancePage") {
                GetAllDistancePage(
                    navigationBack = navigationBack,
                    navToOneDistancePage = listOf(
                        { navController.navigate("GetUpDistancePage") },
                        { navController.navigate("GetDownDistancePage") },
                        { navController.navigate("GetLeftDistancePage") },
                        { navController.navigate("GetFrontDistancePage") },
                    ),
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel,
                    navToHome = {
                        navController.navigate("SearchWifiPage") {
                            popUpTo("SearchWifiPage") {
                                inclusive = true // 또는 false에 따라 동작 결정
                            }
                        }
                    }
                )
            }


            composable(route = "GetUpDistancePage") {
                GetOneDistancePage(
                    id = 1,
                    navigationBack = { navController.navigateUp() },
                    imageResId = R.drawable.up,
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel

                )
            }
            composable(route = "GetDownDistancePage") {
                GetOneDistancePage(
                    id = 2,
                    navigationBack = { navController.navigateUp() },
                    imageResId = R.drawable.down,
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel

                )
            }
            composable(route = "GetLeftDistancePage") {
                GetOneDistancePage(
                    id = 3,
                    navigationBack = { navController.navigateUp() },
                    imageResId = R.drawable.left,
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel
                )
            }
            composable(route = "GetFrontDistancePage") {
                GetOneDistancePage(
                    id = 4,
                    navigationBack = { navController.navigateUp() },
                    imageResId = R.drawable.front,
                    navToHelpPage = { navController.navigate("HelpPage") },
                    mainViewModel = mainViewModel
                )
            }

//            composable(route = "LoadingPage") {
//                LoadingPage(
//                    text = "주변 네트워크를 검색하고 있어요.\n대략 30~40초 정도 걸려요.",
//                    navController = navController
//                )
//            }
            composable(route = "HelpPage") {
                HelpPage(navigationBack = navigationBack)
            }

        }
    }
}
