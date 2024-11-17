package com.xyh.dtb.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyh.dtb.R
import com.xyh.dtb.ui.component.BackGround
import com.xyh.dtb.util.D

//@Preview
@Composable
fun GetStartPage(
    navController: NavController,
) {
    val d: Density = LocalDensity.current
    Surface(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
    ) {
        BackGround(d)
        DtbLogo(d)
        StartButton(d, navController)
    }
}

@Composable
fun DtbLogo(
    d: Density
){
    Box{
        Box(
            modifier = Modifier
                .padding(0.dp)
                .width(D(d, 775))
                .height(D(d, 823))
                .offset(x = D(d, 47), y = D(d, 981))
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .width(D(d, 540))
                        .height(D(d, 540))
                        .padding(0.dp),
                    painter = painterResource(id = R.drawable.capy),
                    contentDescription = "image description",
                    contentScale = ContentScale.None,
                )
                Text(
                    modifier = Modifier
                        .width(D(d, 643))
                        .height(D(d, 362))
                        .padding(start = D(d, 15)),
                    text = "豆豚瓣\n在线观影平台",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xB22F80ED),
                    )
                )
            }
        }
    }
}

@Composable
fun StartButton(
    d: Density,
    navController: NavController
){
    Box{
        Box(
            modifier = Modifier
                .padding(0.dp)
                .width(D(d, 381))
                .height(D(d, 158))
                .offset(x = D(d, 564), y = D(d, 1950))
        ) {
            Button(
                modifier = Modifier
                    .shadow(elevation = D(d, 4), spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                    .border(width = 2.dp, color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 7.dp))
                    .width(D(d, 376))
                    .height(D(d, 158))
                    .background(color = Color.Transparent, shape = RoundedCornerShape(size = 7.dp)),
                onClick = {
                    navController.navigate("login_page")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // 无色按钮
                )
            ){}
            Text(
                modifier = Modifier
                    .padding(start = D(d, 15), top = D(d, 35)),
                text = "Get Started",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}




