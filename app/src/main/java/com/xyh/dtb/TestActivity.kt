package com.xyh.dtb

import android.os.Bundle
import android.provider.Settings.Global
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.xyh.dtb.pojo.dto.LoginDTO
import com.xyh.dtb.pojo.entity.UserData
import com.xyh.dtb.singleton.Api
import com.xyh.dtb.singleton.GlobalMessage
import com.xyh.dtb.store.Store
import com.xyh.dtb.util.copyProperties
import com.xyh.dtb.util.getUsernameFlow
import com.xyh.dtb.util.setUsername
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TestActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalMessage.init(this)
        setContent {
//            Temp()
            Temp2()
//            ScaffoldExample()
//            TabsComponentPreview()
        }
    }
}

@Composable
fun Temp(){
    val coroutineScope = rememberCoroutineScope() // 携程scope
    val context = LocalContext.current

    var userData: UserData? by remember { mutableStateOf(UserData()) }
    var username: String by remember { mutableStateOf("") }
    Surface {
        Column {
            Button(onClick = {
                coroutineScope.launch { // 协同空间
                    userData = Api.login(LoginDTO("xyh", "666666")) as UserData
                    userData ?. let {
                        GlobalMessage.showMessage(it.toString())
                    } ?: run {
                        GlobalMessage.showMessage("登录失败")
                    }
                }
            }) {
                Text(text = userData.toString())
            }

            TextField(
                value = username,
                onValueChange = {
                    username = it
                }
            )

            Button(onClick = {
                coroutineScope.launch {
                    val tmp = getUsernameFlow(context).first()
                    GlobalMessage.showMessage(tmp)
                }
            }) {
                Text(text = "readData")
            }
            Button(onClick = {
                coroutineScope.launch {
                    setUsername(context, username)
                }
            }) {
                Text(text = "setData")
            }
            Button(onClick = {
                coroutineScope.launch {
//                    delUsername(context)
                }
            }) {
                Text(text = "delData")
            }
        }
    }
}

@Composable
fun Temp2() { // remember API
    val coroutineScope = rememberCoroutineScope()
    var userData by remember { mutableStateOf(Store.userData) } // 类不要用remember，会导致数据不更新 类使用viewModel
    var username by remember { mutableStateOf("") } // 本地remember单个属性
    var tmpData by remember { mutableStateOf(Store.myData) } // 本地remember单个属性
    var tmpMutable by remember { Store.myMutable } // 远程remember单个属性
//    var isLoggedIn by rememberSaveable { mutableStateOf(false) } // 记录登录状态 旋转、杀死进程后不会丢失!
    Surface {
        Column {
            Button(onClick = {
                coroutineScope.launch {
                    val result = Api.login(LoginDTO("xyh", "666666"))
                    if (result != null) {
                        copyProperties(result, Store.userData)
                    } else {
                        GlobalMessage.showMessage("登录失败")
                    }
                }
            }) {
                Column {
                    Text(text = userData.username)
                    Text(text = tmpData.toString())
                    Text(text = tmpMutable)
                }
            }

            TextField(
                placeholder = { Text(text = "username") },
                value = username,
                onValueChange = { username = it },
            )

            Button(onClick = {
                coroutineScope.launch {
                    GlobalMessage.showMessage(Store.userData.toString())
                }
            }) {
                Text(text = "readData")
            }

            Button(onClick = {
                coroutineScope.launch {
                    Store.userData = Store.userData.copy(username = username)
                    tmpData = 111
                    tmpMutable = "second"
                }
            }) {
                Text(text = "setData")
            }

            Button(onClick = {
                coroutineScope.launch {
                    Store.userData = UserData()
                }
            }) {
                Text(text = "delData")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var presses by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer( // 神级组件
        drawerState = drawerState, // 侧边抽屉
        gesturesEnabled = drawerState.isOpen,
        drawerContent = { // 抽屉内容
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ){
                Text("Drawer content")
            }
        },
        content = {
            Scaffold( // 脚手架
                snackbarHost = { SnackbarHost(snackbarHostState) }, // 内部消息组件
                topBar = {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text("Top app bar")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Outlined.Home,
                                    contentDescription = "" // Add a valid content description
                                )
                            },
                            label = {
                                Text("Home")
                            },
                            selected = true,
                            onClick = {}
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Outlined.Star,
                                    contentDescription = "" // Add a valid content description
                                )
                            },
                            label = {
                                Text("favor")
                            },
                            selected = false,
                            onClick = {}
                        )
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Outlined.List,
                                    contentDescription = "" // Add a valid content description
                                )
                            },
                            label = {
                                Text("Details")
                            },
                            selected = false,
                            onClick = {}
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        presses++
                        scope.launch {
                            snackbarHostState.showSnackbar("Add clicked")
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text =
                            """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
                        )
                    }
                }
            )
        }
    )

}