package com.example.mangaloo.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mangaloo.navigation.NavRoutes
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mangaloo.navigation.NavBarItems
import com.example.mangaloo.ui.chapter.list.ChapterList
import com.example.mangaloo.ui.chapter.list.ChapterListViewModel
import com.example.mangaloo.ui.manga.library.MangaLibrary
import com.example.mangaloo.ui.manga.search.MangaSearch
import com.example.mangaloo.ui.manga.search.MangaSearchViewModel

@Composable
fun MainScreen(navController: NavHostController) {
    val isNavBarVisible = remember { mutableStateOf(true) }
    Scaffold(
        content = { padding ->
            Box(Modifier.padding(padding)) {
                NavigationHost(navController = navController, isNavBarVisible)
            }
        },
        bottomBar = {
            if (isNavBarVisible.value) {
                NavBar(navController = navController)
            }
        }

    )

}

@Composable
fun NavigationHost(navController: NavHostController, isNavbarVisible: MutableState<Boolean>) {
    NavHost(navController = navController,
        startDestination = NavRoutes.mangaLibrary.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(NavRoutes.mangaSearch.route) {
            isNavbarVisible.value = true
            val viewModel: MangaSearchViewModel = viewModel()
            MangaSearch(viewModel)
        }
        composable(NavRoutes.mangaLibrary.route) {
            isNavbarVisible.value = true
            MangaLibrary { route: String -> navController.navigate(route) }
        }
        composable(NavRoutes.chapterList.route + "/{mangaId}") { backStackEntry ->
            isNavbarVisible.value = false
            val mangaId= backStackEntry.arguments?.getString("mangaId")?:""
            val viewModel =
                hiltViewModel<ChapterListViewModel, ChapterListViewModel.ChapterListViewModelFactory> { factory ->
                    factory.create(mangaId)
                }
            ChapterList(viewModel)
        }

    }
}

@Composable
fun NavBar(navController: NavHostController) {
    NavigationBar(Modifier.height(64.dp)) {
        var selectedIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        NavBarItems.BarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (selectedIndex == index) {
                            item.selected
                        } else item.unselected,
                        contentDescription = null,
                        modifier = if (selectedIndex == index) Modifier.fillMaxSize(0.55F)
                        else Modifier.fillMaxSize(0.5F)
                    )
                })
        }

    }
}
