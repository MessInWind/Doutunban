package com.xyh.dtb.util

import androidx.navigation.NavController

fun printNavStack(navController: NavController) { // 打印当前的route和上一级route
    val currentEntry = navController.currentBackStackEntry
    val previousEntry = navController.previousBackStackEntry
    println("Navigation Stack:")
    println("Current: ${currentEntry?.destination?.route}")
    println("Previous: ${previousEntry?.destination?.route}")
}

// API
// navController.popBackStack() // 返回上一级
// navController.navigate("third_page"){popUpTo("one_page")} // 跳转到third_page并清除one_page
// navController.navigate("one_page"){ // 跳转到one_page并清除之前的所有页面
//     popUpTo(navController.graph.startDestinationId) {
//         inclusive = true // 目标页面及其之前的所有页面都会被清除
//     }
// }

//@Composable
//fun DefineNavController() { // 设置导航器
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "one_page") {
//        composable("one_page") { OnePage(navController) }
//        composable("two_page") { TwoPage(navController) }
//        composable("third_page") { ThirdPage(navController) }
//    }
//}