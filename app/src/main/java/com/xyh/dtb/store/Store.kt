package com.xyh.dtb.store

import androidx.compose.runtime.mutableStateOf
import com.xyh.dtb.pojo.entity.UserData

object Store { // 全局存储
    var userData: UserData = UserData() // 引用不变看看
    var movieId: Int = 1291543
    var personId: Int = 1036369


    // 样例数据
    var myData: Int = 0
    var myMutable = mutableStateOf("first")
}

//var userData by remember { mutableStateOf(UserDataStore.getUserData()) }