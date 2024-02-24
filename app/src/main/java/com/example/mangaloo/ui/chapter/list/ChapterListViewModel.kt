package com.example.mangaloo.ui.chapter.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaloo.api.MangaDexApi
import com.example.mangaloo.model.api.ApiChapter
import com.example.mangaloo.model.api.manga.ApiManga
import com.example.mangaloo.model.api.manga.ApiMangaStatsResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
@HiltViewModel(assistedFactory = ChapterListViewModel.ChapterListViewModelFactory::class)
class ChapterListViewModel@AssistedInject constructor(
    @Assisted mangaId: String
) : ViewModel() {

    val chapters = MutableStateFlow(listOf<ApiChapter>())
    val manga = MutableStateFlow<ApiManga?>(null)
    val follows = MutableStateFlow<Number>(0)
    val rating = MutableStateFlow<Number>(0)
    val coverLink =MutableStateFlow("")


    init {
        getChapters(mangaId)
        getManga(mangaId)
        getStats(mangaId)
    }
    private fun getStats(mangaId: String) {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getMangaStats(mangaId)
                if (listResult.isSuccessful && listResult.body() != null) {
                    val data = listResult.body()!!
                    follows.value = data.statistics[mangaId]?.follows ?:0
                    rating.value = data.statistics[mangaId]?.rating?.average ?:0
                }
            } catch (e: HttpException) {
                Log.d("Errorrrrr", "failed")
            } catch (e: IOException) {
                Log.d("Errorrrrr", "io")
            }

        }
    }

    private fun getChapters(mangaId: String) {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getMangaChapters(mangaId,150, listOf("en"))
                if (listResult.isSuccessful && listResult.body() != null) {
                    chapters.value = listResult.body()!!.data

                    Log.d("Chapter", chapters.value.count().toString())
                }
            } catch (e: HttpException) {
                Log.d("Errorrrrr", "failed")
            } catch (e: IOException) {
                Log.d("Errorrrrr", "io")
            }

        }
    }
    private fun getManga(mangaId: String) {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getSingleManga(
                    mangaId, listOf("cover_art", "author")
                )
                if (listResult.isSuccessful && listResult.body() != null) {
                    manga.value = listResult.body()!!.data
                    val rel = manga.value?.relationships?.filter { it.type == "cover_art" }
                    val coverFile = rel?.get(0)?.attributes?.fileName
                    coverLink.value = if (coverFile == null) "" else "https://uploads.mangadex.org/covers/$mangaId/$coverFile"
                }
            } catch (e: HttpException) {
                Log.d("Errorrrrr", "failed")
            } catch (e: IOException) {
                Log.d("Errorrrrr", "io")
            }

        }
    }
    @AssistedFactory
    interface ChapterListViewModelFactory {
        fun create(mangaId: String): ChapterListViewModel
    }
}