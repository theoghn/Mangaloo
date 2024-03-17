package com.example.mangaloo.ui.manga.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaloo.database.AppRepository
import com.example.mangaloo.model.db.Manga
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaLibraryViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    val mangas = MutableStateFlow<List<Manga>>(emptyList())
    val searchTitle = MutableStateFlow("")

    init {
        viewModelScope.launch{
            val result = repository.getAllMangas()
            mangas.update { result }
            Log.d("MangaLibrary",mangas.value.count().toString())

        }
    }
    fun updateList(){
        viewModelScope.launch {
            val result = repository.getAllMangas()
            if (result != mangas.value){
                mangas.update { result }
                Log.d("MangaLibrary","after update "+mangas.value.count().toString())
            }
        }
    }

    fun updateSearch(newTitle:String)=searchTitle.update { newTitle }

}