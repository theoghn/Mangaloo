package com.example.mangaloo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BaseViewModel: ViewModel() {
    val response = MutableStateFlow(ChapterResponse("","", emptyList()))
    init {
        getMarsPhotos()
    }
    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MangaDexApi.retrofitService.getPhotos("304ceac3-8cdb-4fe7-acf7-2b6ff7a60613", listOf("en"))
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