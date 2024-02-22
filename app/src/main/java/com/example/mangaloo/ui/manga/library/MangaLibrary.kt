package com.example.mangaloo.ui.manga.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mangaloo.navigation.NavRoutes

@Composable
fun MangaLibrary(navigate: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { navigate(NavRoutes.chapterList.route) }) {

        }
    }
}