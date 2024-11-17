package com.xyh.dtb.singleton

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// API
// 必须放在compose里面，比较难用，不用了
// val scope = rememberCoroutineScope()
// val snackbarHostState = remember { SnackbarHostState() }
// SnackbarManager.init(snackbarHostState, scope)
// SnackbarHost(hostState = snackbarHostState) // 放在底部，消息显示顶层

// onClick = {
//     SnackbarManager.showMessage("Snackbar")
// }

object SnackbarManager { // 普通消息组件
    private lateinit var snackbarHostState: SnackbarHostState
    private lateinit var scope: CoroutineScope

    fun init(snackbarHostState: SnackbarHostState, scope: CoroutineScope) { // 初始化
        SnackbarManager.snackbarHostState = snackbarHostState
        SnackbarManager.scope = scope
    }

    fun showMessage(message: String) { // 展示信息
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}
