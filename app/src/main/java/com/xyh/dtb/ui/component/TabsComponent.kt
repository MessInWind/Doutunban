package com.xyh.dtb.ui.component

import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

object TabsOptionsData {
    val containerColor: Color = Color(0xFFC6A2FA)
    val contentColor: Color = Color(0xFFFFFFFF)
}

@Composable
fun TabsComponentPreview(
    tabsOptionsData: TabsOptionsData = TabsOptionsData
) {

    val tabItems = listOf(
        Icons.Rounded.Image to "Photos",
        Icons.Rounded.Videocam to "Video",
        Icons.Rounded.MusicNote to "Audio",
    )

    val scope = rememberCoroutineScope()

    // androidx.compose.foundation
    val pagerState = rememberPagerState { 3 }

    var useIcon by remember { mutableStateOf(true) }
    var useLabel by remember { mutableStateOf(true) }
    var showDivider by remember { mutableStateOf(true) }
    var isTabEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            contentColor = tabsOptionsData.contentColor,
            containerColor = tabsOptionsData.containerColor,
            selectedTabIndex = pagerState.currentPage,
            divider = {
                if (showDivider) Divider()
            },
            indicator = { tabPositions ->
                if(showDivider)
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .fillMaxSize()
                        .padding(4.dp)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFAB74FA),
                            shape = RectangleShape
                        )
                )
            }
        ) {
            tabItems.forEachIndexed { i, (icon, label) ->
                Tab(
                    enabled = isTabEnabled,
                    selected = pagerState.currentPage == i,
                    selectedContentColor = LocalContentColor.current,
                    unselectedContentColor = LocalContentColor.current,
                    text = if (useLabel) {
                        { // @Composable
                            Text(
                                text = label
                            )
                        }
                    } else null,
                    icon = if (useIcon) {
                        { // @Composable
                            Icon(
                                imageVector = icon,
                                contentDescription = null
                            )
                        }
                    } else null,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(i)
                        }
                    }
                )
            }
        }

        // androidx.compose.foundation
        HorizontalPager(
            state = pagerState
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when(page){
                    0->{
                        Text("Page ${page + 1}")
                        Spacer(modifier = Modifier.height(8.dp))
                        TabsOption(
                            useIcon = useIcon,
                            useLabel = useLabel,
                            showDivider = showDivider,
                            isTabEnabled = isTabEnabled,
                            onUseIconChanged = { use ->
                                useIcon = use
                            },
                            onUseLabelChanged = { use ->
                                useLabel = use
                            },
                            onShowDividerChanged = { show ->
                                showDivider = show
                            },
                            onTabEnabledChanged = { enabled ->
                                isTabEnabled = enabled
                            }
                        )
                    }
                    1->{
                        Text("Page ${page + 1}")
                        Spacer(modifier = Modifier.height(8.dp))
                        TabsOption(
                            useIcon = useIcon,
                            useLabel = useLabel,
                            showDivider = showDivider,
                            isTabEnabled = isTabEnabled,
                            onUseIconChanged = { use ->
                                useIcon = use
                            },
                            onUseLabelChanged = { use ->
                                useLabel = use
                            },
                            onShowDividerChanged = { show ->
                                showDivider = show
                            },
                            onTabEnabledChanged = { enabled ->
                                isTabEnabled = enabled
                            }
                        )
                    }
                    2->{
                        Text("Page ${page + 1}")
                        Spacer(modifier = Modifier.height(8.dp))
                        TabsOption(
                            useIcon = useIcon,
                            useLabel = useLabel,
                            showDivider = showDivider,
                            isTabEnabled = isTabEnabled,
                            onUseIconChanged = { use ->
                                useIcon = use
                            },
                            onUseLabelChanged = { use ->
                                useLabel = use
                            },
                            onShowDividerChanged = { show ->
                                showDivider = show
                            },
                            onTabEnabledChanged = { enabled ->
                                isTabEnabled = enabled
                            }
                        )
                    }
                }
            }
        }
    }
}


