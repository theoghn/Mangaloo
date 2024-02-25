package com.example.mangaloo.ui.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mangaloo.navigation.NavRoutes
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangaloo.navigation.NavBarItems
import com.example.mangaloo.ui.chapter.list.ChapterList
import com.example.mangaloo.ui.chapter.list.ChapterListViewModel
import com.example.mangaloo.ui.chapter.reader.ChapterReader
import com.example.mangaloo.ui.chapter.reader.ChapterReaderViewModel
import com.example.mangaloo.ui.home.Home
import com.example.mangaloo.ui.home.HomeViewModel
import com.example.mangaloo.ui.manga.library.MangaLibrary
import com.example.mangaloo.ui.manga.search.MangaSearch
import com.example.mangaloo.ui.manga.search.MangaSearchViewModel

@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel) {
    val navController = rememberNavController()
    val isNavBarVisible = remember { mutableStateOf(true) }
    Scaffold(
        content = { padding ->
            Box(Modifier.padding(padding)) {
                NavigationHost(navController = navController, isNavBarVisible)
            }
        },
        bottomBar = {
            if (isNavBarVisible.value) {
                NavBar(navController = navController, mainScreenViewModel)
            }
        }

    )

}

@Composable
fun NavigationHost(navController: NavHostController, isNavbarVisible: MutableState<Boolean>) {
    val searchViewModel: MangaSearchViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(navController = navController,
        startDestination = NavRoutes.homeView.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(NavRoutes.homeView.route) {
            isNavbarVisible.value = true
            Home(homeViewModel)
        }

        composable(NavRoutes.mangaSearch.route) {
            isNavbarVisible.value = true
            MangaSearch(
                searchViewModel,
                navigate = { route: String -> navController.navigate(route) })
        }

        composable(NavRoutes.mangaLibrary.route) {
            isNavbarVisible.value = true
            MangaLibrary { route: String -> navController.navigate(route) }
        }


        composable(NavRoutes.chapterList.route + "/{mangaId}") { backStackEntry ->
            isNavbarVisible.value = false
            val mangaId = backStackEntry.arguments?.getString("mangaId") ?: ""
            val viewModel =
                hiltViewModel<ChapterListViewModel, ChapterListViewModel.ChapterListViewModelFactory> { factory ->
                    factory.create(mangaId)
                }
            ChapterList(
                viewModel,
                goBack = { navController.popBackStack() },
                openChapter = { chapterId: String -> navController.navigate(NavRoutes.chapterReader.route + "/" + chapterId) }
            )
        }


        composable(NavRoutes.chapterReader.route + "/{chapterId}") { backStackEntry ->
            isNavbarVisible.value = false
            val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
            val viewModel =
                hiltViewModel<ChapterReaderViewModel, ChapterReaderViewModel.ChapterListViewModelFactory> { factory ->
                    factory.create(chapterId)
                }
            ChapterReader(viewModel)
        }

    }
}

@Composable
fun NavBar(navController: NavHostController, mainScreenViewModel: MainScreenViewModel) {

    BottomNavigation(Modifier.height(50.dp)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        NavBarItems.BarItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.unselected,
                        tint = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) Color.White else Color.Gray,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.55F)
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }


}
