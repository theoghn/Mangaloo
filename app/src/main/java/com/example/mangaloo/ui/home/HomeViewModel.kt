package com.example.mangaloo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaloo.api.MangaDexApi
import com.example.mangaloo.model.api.manga.ApiManga
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel : ViewModel() {
    val selectedIndex = MutableStateFlow(0)
    val popularManga = MutableStateFlow(emptyList<ApiManga?>())
    val bestRatedManga = MutableStateFlow(emptyList<ApiManga?>())

    init {
        Log.d("HomeView","init block")
        getManga("followedCount")

        getManga("rating")
    }

    private fun getManga(ord: String) {
        viewModelScope.launch {
            try {
                val order = mapOf(
                    ord to "desc",
                )
                Log.d("Manga", popularManga.value.count().toString())

                val finalOrderQuery = mutableMapOf<String, String>()

                for ((key, value) in order) {
                    finalOrderQuery["order[$key]"] = value
                }
                val listResult = MangaDexApi.retrofitService.getManga(
                    "", listOf("cover_art", "author"), 10,
                    listOf("safe", "suggestive","erotica"), finalOrderQuery
                )

                if (listResult.isSuccessful && listResult.body() != null) {
                    if(ord == "followedCount"){
                        popularManga.update { listResult.body()!!.data }
                    }
                    else{
                        bestRatedManga.update { listResult.body()!!.data }
                    }
                    Log.d("Manga", listResult.body()!!.result)

                }
            } catch (e: HttpException) {
                Log.d("Errorrrrr", "failed")
            } catch (e: IOException) {
                Log.d("Errorrrrr", "io")
            }

        }
    }

    fun changeIndex(newIndex: Int) = selectedIndex.update { newIndex }
}