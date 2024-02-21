package com.example.mangaloo.database

import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDAO: AppDAO
) {
}