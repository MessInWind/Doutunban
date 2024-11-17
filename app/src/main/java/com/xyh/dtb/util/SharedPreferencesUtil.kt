package com.xyh.dtb.util

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Global初始化 导入: import com.xyh.dtb.util.dataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userConfig") // 数据库名

val USERNAME = stringPreferencesKey("username") // Key名/val类型
fun getUsernameFlow(context: Context): Flow<String> {
    return context.dataStore.data.map { preferences: Preferences ->
        preferences[USERNAME] ?: ""
    }
}
suspend fun setUsername(context: Context, username: String) {
    context.dataStore.edit { settings ->
        settings[USERNAME] = username
    }
}

val PASSWORD = stringPreferencesKey("password") // Key名/val类型
fun getPasswordFlow(context: Context): Flow<String> {
    return context.dataStore.data.map { preferences: Preferences ->
        preferences[PASSWORD] ?: ""
    }
}
suspend fun setPassword(context: Context, password: String) {
    context.dataStore.edit { settings ->
        settings[PASSWORD] = password
    }
}


val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

@Composable
private fun Test(){ // 携程API
    Box{
        var coroutineScope = rememberCoroutineScope()
        val context: Context = LocalContext.current // 获取上下文

        val exampleCounterFlow: Flow<Int> = context.dataStore.data.map { preferences ->
            preferences[EXAMPLE_COUNTER] ?: 0
        }

        runBlocking { // 阻塞进程直到获取到数据
            var cnt = exampleCounterFlow.map { it }.first()
        }

        runBlocking {
            var username: String = getUsernameFlow(context).first() // 标准获取data
        }

        LaunchedEffect(Unit) { // 初始化携程空间 仅在进入Composition时执行一次
            // Perform some suspend operations
            // This coroutine will be cancelled when the composable leaves the composition
            setUsername(context, "JoJo") // 标准设置data
        }

        DisposableEffect(key1 = context) { // 析构时调用
            onDispose {
                coroutineScope.launch {
//                    delUsername(context)
                }
            }
        }

        SideEffect { // 每次重组时调用
            coroutineScope.launch {
//                delUsername(context)
            }
        }

        Button(onClick = {
            coroutineScope.launch { // 组件内携程空间
                setUsername(context, "JoJo")
            }
        }) {

        }
    }
}