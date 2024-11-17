package com.xyh.dtb.ui.page

import android.provider.Settings.Global
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.xyh.dtb.pojo.dto.IdDTO
import com.xyh.dtb.pojo.entity.MoviesData
import com.xyh.dtb.pojo.entity.PersonData
import com.xyh.dtb.pojo.entity.RandomMoviesData
import com.xyh.dtb.singleton.Api
import com.xyh.dtb.singleton.GlobalMessage
import com.xyh.dtb.store.Store
import com.xyh.dtb.ui.component.ExpandableText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val listState = rememberLazyStaggeredGridState()

    var presses by remember { mutableIntStateOf(0) }
    // 列表项
    val pages = remember { mutableStateListOf<RandomMoviesData>() }

    LaunchedEffect(Unit){
        val result =  Api.getMoviesRandom(Unit)
        if(result != null){
            val randomMoviesList: List<RandomMoviesData> = result as List<RandomMoviesData>
            pages.addAll(randomMoviesList)
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == pages.size - 1) { // 滑到底部，加载更多
                    val result =  Api.getMoviesRandom(Unit)
                    if(result != null){
                        val randomMoviesList: List<RandomMoviesData> = result as List<RandomMoviesData>
                        pages.addAll(randomMoviesList)
                    }
                }
            }
    }

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
                        colors = TopAppBarDefaults.topAppBarColors(
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
                            val result = Api.getPersonInfoById(IdDTO(id = 1036369))
                            if(result != null){
                                val person = result as PersonData
                                GlobalMessage.showMessage(person.toString())
                            }
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
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2), // 2列
                            contentPadding = PaddingValues(8.dp, 8.dp), // 整体内边距
                            verticalItemSpacing = 4.dp, // item 和 item 之间的纵向间距
                            horizontalArrangement = Arrangement.spacedBy(8.dp), // item 和 item 之间的横向间距
                            state = listState, // 列表滚动到底部
                        ){
                            itemsIndexed(pages, key = { _, p -> p.id }) { index, item ->
                                Card(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 4.dp,
                                        pressedElevation = 8.dp,
                                    ),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = item.name ?: "",
                                            style = MaterialTheme.typography.titleLarge,
                                            modifier = Modifier
                                                .clickable{
                                                    Store.movieId = item.id
                                                    navController.navigate("movie_page")
                                                }
                                        ) // 电影名
                                        Text(text = "评分: " + item.rating.toString()) // 评分
                                        LinearProgressIndicator(
                                            progress = item.rating.toFloat() / 10,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = MaterialTheme.colorScheme.secondary,
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Image(
                                            painter = rememberAsyncImagePainter(model = item.img ?: ""),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(200.dp)
                                                .clip(RectangleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                        ExpandableText(
                                            text = item.summary ?: "",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    )

}

