package com.xyh.dtb.util

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

// LocalDensity.current 获取当前屏幕的密度。 density = 像素 / dp
// with(density) { 200.toDp() } 和 with(density) { 300.toDp() } 将像素值 200 和 300 转换为 dp。
// Modifier.offset(x = offsetX, y = offsetY) 使用转换后的 dp 值设置图片的位置。

// D(d, )

fun D(d: Density, n: Int): Dp{
    return with(d) { n.toDp() }
}