package com.example.mangaloo.ui.chapter.reader

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaloo.api.MangaDexApi
import com.example.mangaloo.ui.chapter.list.ChapterListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
@HiltViewModel(assistedFactory = ChapterReaderViewModel.ChapterListViewModelFactory::class)
class ChapterReaderViewModel@AssistedInject constructor(
    @Assisted chapterId: String
) :ViewModel(){
    val hash = MutableStateFlow("")
    val baseUrl = MutableStateFlow("")
    val imagesLinks = MutableStateFlow(emptyList<String>())

    init {
        getImages(chapterId)
    }
    private fun getImages(chapterId: String) {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getChapterImages(
                    chapterId
                )
                if (listResult.isSuccessful && listResult.body() != null) {
                    baseUrl.update {listResult.body()!!.baseUrl }
                    hash.update {listResult.body()!!.chapter.hash}
                    imagesLinks.update{ listResult.body()!!.chapter.dataSaver }

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
        fun create(chapterId: String): ChapterReaderViewModel
    }
}