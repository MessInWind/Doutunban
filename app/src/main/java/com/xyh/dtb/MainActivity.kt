package com.xyh.dtb

import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xyh.dtb.ui.page.GetStartPage
import com.xyh.dtb.ui.page.LoginPage
import com.xyh.dtb.ui.page.RegisterPage
import com.xyh.dtb.ui.theme.DoutunbanTheme
import com.xyh.dtb.singleton.GlobalMessage
import com.xyh.dtb.ui.page.HomePage
import com.xyh.dtb.ui.page.MoviePage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalMessage.init(this)
        setContent {
            DoutunbanTheme {
                DefineNavController()
            }
        }
    }

    @Composable
    fun DefineNavController() { // 设置导航器
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "get_start_page") {
            composable("get_start_page") { GetStartPage(navController) }
            composable("login_page") {
                LoginPage(
                    navController,
                )
            }
            composable("register_page") {
                RegisterPage(
                    navController,
                )
            }
            composable("home_page") {
                 HomePage(
                     navController,
                 )
            }
            composable("movie_page"){
                MoviePage(
                    navController,
                )
            }
        }
    }
}

// 导航器间跳转
// @Composable
// fun MainScreen() {
//     val mainNavController = rememberNavController()
//     NavHost(navController = mainNavController, startDestination = "home") {
//         composable("home") { HomeScreen(mainNavController) }
//         composable("settings") { SettingsScreen() }
//     }
// }
//
// @Composable
// fun HomeScreen(mainNavController: NavController) {
//     val homeNavController = rememberNavController()
//     NavHost(navController = homeNavController, startDestination = "dashboard") {
//         composable("dashboard") { DashboardScreen(homeNavController) }
//         composable("profile") { ProfileScreen(homeNavController) }
//     }
//     // Button to navigate to SettingsScreen
//     Button(onClick = { mainNavController.navigate("settings") }) {
//         Text("Go to Settings")
//     }
// }

