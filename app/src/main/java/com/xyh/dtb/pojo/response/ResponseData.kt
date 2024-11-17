package com.xyh.dtb.pojo.response

data class ResponseData<T>(
    val code: Int,
    val msg: String?,
    val data: T?
)