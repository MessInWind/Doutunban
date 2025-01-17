package com.xyh.dtb.ui.page

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyh.dtb.R
import com.xyh.dtb.pojo.dto.LoginDTO
import com.xyh.dtb.pojo.entity.UserData
import com.xyh.dtb.singleton.Api
import com.xyh.dtb.singleton.GlobalMessage
import com.xyh.dtb.store.Store
import com.xyh.dtb.ui.component.BackGround
import com.xyh.dtb.util.D
import com.xyh.dtb.util.copyProperties
import com.xyh.dtb.util.getPasswordFlow
import com.xyh.dtb.util.getUsernameFlow
import com.xyh.dtb.util.setPassword
import com.xyh.dtb.util.setUsername
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//@Preview
@Composable
fun LoginPage(
    navController: NavController,
) {
//    var isLoggedIn by rememberSaveable { mutableStateOf(false) } // 记录登录状态 旋转, 杀死进程后会丢失
    val d: Density = LocalDensity.current
    Surface(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
    ) {
        BackGround(d)
        Login(
            d,
            navController,
        )
        Auth(d)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    d: Density,
    navController: NavController,
){
    val scope = rememberCoroutineScope() // 携程scope
    val context: Context = LocalContext.current // 获取上下文
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        username = getUsernameFlow(context).first()
        password = getPasswordFlow(context).first()
    }

    Box{
        Box(
            modifier = Modifier
                .padding(0.dp)
                .width(D(d, 946))
                .height(D(d, 1253))
                .offset(x = D(d, 55), y = D(d, 900))
        ){
            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2F80ED),
                ),
            )
            Text(
                modifier = Modifier
                    .offset(x = D(d, 10), y = D(d, 224)),
                text = "Username",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2F80ED),
                )
            )
            Text(
                modifier = Modifier
                    .offset(x = D(d, 10), y = D(d, 460)),
                text = "Password",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2F80ED),
                )
            )
            TextField(
                placeholder = { Text(text = "Username") },
                value = username,
                onValueChange = {
                    username = it
                },
                modifier = Modifier
                    .offset(x = D(d, 10), y = D(d, 288))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2F80ED),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(0.5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // 无色
                    focusedIndicatorColor = Color.Transparent, // 移除聚焦时的下划线
                    unfocusedIndicatorColor = Color.Transparent // 移除未聚焦时的下划线
                )
            )
            TextField(
                placeholder = { Text(text = "Password") },
                value = password,
                visualTransformation = PasswordVisualTransformation(), // 密码效果
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .offset(x = D(d, 10), y = D(d, 525))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2F80ED),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(0.5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // 无色
                    focusedIndicatorColor = Color.Transparent, // 移除聚焦时的下划线
                    unfocusedIndicatorColor = Color.Transparent // 移除未聚焦时的下划线
                )
            )
            Button(
                onClick = {
                    scope.launch { // 协同空间
                        val result = Api.login(LoginDTO(username, password)) as? UserData
                        if (result != null){ // 需要手动判空
                            copyProperties(result, Store.userData)
                            GlobalMessage.showMessage("登陆成功 ${Store.userData.username}")

                            setUsername(context, username) // 持久化
                            setPassword(context, password)

                            navController.navigate("home_page"){
                                popUpTo("login_page")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .offset(x = D(d, 567), y = D(d, 991))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 7.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // 无色
                ),
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Button(
                onClick = {
                    navController.navigate("register_page")
                        { popUpTo("get_start_page") }
                },
                modifier = Modifier
                    .offset(x = D(d, 5), y= D(d, 1100)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // 无色
                ),
            ){
                Text(
                    text = "New Here? Register",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}

@Composable
private fun Auth(
    d: Density
){
    Box{
        Box(
            modifier = Modifier
                .padding(0.dp)
                .width(D(d, 439))
                .height(D(d, 131))
                .offset(x = D(d, 66), y = D(d, 1750))
        ){
            Button(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = Color(0xFF2F80ED),
                    shape = RoundedCornerShape(size = 7.dp)
                ),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // 无色
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gitee_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}







