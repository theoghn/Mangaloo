package com.example.mangaloo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            selected = Icons.Filled.Home,
            unselected = Icons.Outlined.Home,
            route = "HomeView"
        ),
        BarItem(
            title = "Find",
            selected = Icons.Filled.Search,
            unselected = Icons.Outlined.Search,
            route = "MangaSearch"
        ),
        BarItem(
            title = "Library",
            selected = Icons.Outlined.Person,
            unselected = Icons.Outlined.Person,
            route = "MangaLibrary"
        )



    )
}