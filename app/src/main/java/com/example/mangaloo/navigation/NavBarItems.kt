package com.example.mangaloo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.res.vectorResource
import com.example.mangaloo.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            selected = Icons.Outlined.Home,
            unselected = Icons.Outlined.Home,
            route = "HomeView"
        ),
        BarItem(
            title = "Library",
            selectedResId =  R.drawable.book,
            unselected = Icons.Outlined.Person,
            route = "MangaLibrary"
        ),
        BarItem(
            title = "Find",
            selected = Icons.Outlined.Search,
            unselected = Icons.Outlined.Search,
            route = "MangaSearch"
        )




    )
}