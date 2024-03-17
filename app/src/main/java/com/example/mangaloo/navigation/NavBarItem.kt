package com.example.mangaloo.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem(
    val title: String,
    val selected: ImageVector? = null,
    val selectedResId: Int = 0,
    val unselected: ImageVector,
    val route: String
)