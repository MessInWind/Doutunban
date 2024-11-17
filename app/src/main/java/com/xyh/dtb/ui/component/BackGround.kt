package com.xyh.dtb.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.xyh.dtb.R
import com.xyh.dtb.util.D


@Composable
fun BackGround(
    d: Density
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            modifier = Modifier
                .padding(0.dp)
                .width(D(d, 1080))
                .height(D(d, 2400)),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.None, // 不加的话图片不全屏
        )
    }
}