package com.example.mangaloo.ui.manga.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaloo.api.MangaDexApi
import com.example.mangaloo.model.api.manga.ApiMangaResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

//@HiltViewModel
class MangaSearchViewModel : ViewModel() {
    val response = MutableStateFlow(ApiMangaResponse("", "", emptyList()))
    val searchTitle = MutableStateFlow("")

    fun updateSearch(newTitle:String)=searchTitle.update { newTitle }

//    init {
//        getManga()
//    }
/*
        Wasted time, last chapter uploaded is not always the the last chapter, should use the feed but would have to do a lot of calls for each manga
        just set the status
 */

//    private fun getChaptersNumbers() {
//        val chapterIds: List<String?> =
//            response.value.data.map { it?.attributes?.latestUploadedChapter }
//        viewModelScope.launch {
//            try {
//                if (chapterIds.isNotEmpty()) {
//                    val chapterResult = MangaDexApi.retrofitService.getChapter(chapterIds)
//                    if (chapterResult.isSuccessful && chapterResult.body() != null) {
//                        val test = chapterResult.body()!!
//                        val chapterList = chapterResult.body()!!.data.map { it.attributes.chapter }
//                        test.data.forEach(){
//                            Log.d("Theo", it.id)
//                        }
//                        if (response.value.data.count() == chapterList.count()) {
//                            response.update {
//                                    currentResponse ->
//                                currentResponse.copy(
//                                    data = currentResponse.data.mapIndexed { index, manga ->
//                                        manga?.let {
//                                            it.copy(attributes = it.attributes.copy(lastChapter = chapterList.getOrNull(index)))
//                                        }
//                                    }
//                                )
//
//                            }
//                            response.value.data.forEachIndexed { index, manga ->
//                                manga?.attributes?.lastChapter = chapterList[index]
//                            }
//                        }
//                    }
//                }
//            } catch (e: HttpException) {
//                Log.d("Errorrrrr", "failed")
//            }
//        }
//    }


    fun getManga(title: String) {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getManga(
                    title, listOf("cover_art", "author"), 5,
                    listOf("safe", "suggestive")
                )
                if (listResult.isSuccessful && listResult.body() != null) {
                    response.value = listResult.body()!!
                }
            } catch (e: HttpException) {
                Log.d("Errorrrrr", "failed")
            } catch (e: IOException) {
                Log.d("Errorrrrr", "io")
            }

        }
    }
}