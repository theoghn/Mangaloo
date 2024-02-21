package com.example.mangaloo.ui.library.manga

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaloo.api.MangaDexApi
import com.example.mangaloo.model.api.manga.ApiMangaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
//@HiltViewModel
class TestViewModel: ViewModel() {
    val response = MutableStateFlow(ApiMangaResponse("","",null))
    init {
        getManga()
    }
    private fun getManga() {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getManga("304ceac3-8cdb-4fe7-acf7-2b6ff7a60613")
                if (listResult.isSuccessful && listResult.body() != null) {
                    response.value = listResult.body()!!
                }
            }
            catch (e: HttpException){
                Log.d("Errorrrrr","failed")
            }
            catch (e: IOException){
                Log.d("Errorrrrr","io")
            }

        }
    }
}