package com.xyh.dtb.pojo.entity

data class UserData(
    var id: Int = -1,
    var username: String = "",
    var phone: String? = "",
    var email: String? = "",
    var joinGroup: String? = "",
    var giteeuuid: String? = "",
    var token: String = "",
)