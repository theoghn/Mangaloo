package com.example.mangaloo.navigation

sealed class NavRoutes(val route: String) {
    data object mangaSearch : NavRoutes("MangaSearch")
    data object mangaLibrary : NavRoutes("MangaLibrary")
    data object chapterList : NavRoutes("ChapterList")
    data object chapterReader : NavRoutes("ChapterReader")






}