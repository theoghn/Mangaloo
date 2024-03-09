package com.example.mangaloo.ui.manga.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import coil.memory.MemoryCache
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
    val isLoading = MutableStateFlow(false)

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


    @OptIn(ExperimentalCoilApi::class)
    fun getManga(title: String, context: Context) {
        if(response.value.data.isNotEmpty()){
            for(manga in response.value.data) {
                val mangaId = manga?.id
                val rel = manga?.relationships?.filter { it.type == "cover_art" }
                val coverLink = rel?.get(0)?.attributes?.fileName
                val coverFinalLink =
                    if (coverLink == null) "" else "https://uploads.mangadex.org/covers/$mangaId/$coverLink"
                if (!coverLink.isNullOrBlank()){
                    context.imageLoader.diskCache?.remove(coverFinalLink)
                    context.imageLoader.memoryCache?.remove(MemoryCache.Key(coverFinalLink))
                    Log.d("Cache","image uncached")
                }
            }

        }
        isLoading.update { true }
        viewModelScope.launch {
            val order = mapOf(
                "rating" to "desc",
                "followedCount" to "desc"
            )
            val finalOrderQuery = mutableMapOf<String, String>()

            for ((key, value) in order) {
                finalOrderQuery["order[$key]"] = value
            }
            try {
                val listResult = MangaDexApi.retrofitService.getManga(
                    title, listOf("cover_art", "author"), 5,
                    listOf("safe", "suggestive"),finalOrderQuery
                )
                if (listResult.isSuccessful && listResult.body() != null) {
                    response.value = listResult.body()!!
                    isLoading.update { false }

                }
            } catch (e: HttpException) {
                Log.d("Errorrrrr", "failed")
            } catch (e: IOException) {
                Log.d("Errorrrrr", "io")
            }

        }
    }
}