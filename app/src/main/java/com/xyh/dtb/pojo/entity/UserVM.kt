package com.xyh.dtb.pojo.entity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// val id: Int = -1,
// val username: String = "",
// val phone: String? = "",
// val email: String? = "",
// val joinGroup: String = "",
// val giteeuuid: String? = "",
// val token: String = "",


// userVM.value?.id = 100 // 赋值
// userVM.value = userVM.value?.copy() // 更新视图

class UserVM: ViewModel() {
    val userData = MutableLiveData(UserData())

    fun updateUserData(){
        userData.value = userData.value?.copy()
    }
}