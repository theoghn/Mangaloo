package com.example.mangaloo.ui.chapter.reader

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ChapterReader(viewModel: ChapterReaderViewModel) {
    val hash by viewModel.hash.collectAsState()
    val baseUrl by viewModel.baseUrl.collectAsState()
    val imageLinks by viewModel.imagesLinks.collectAsState()

    LazyColumn(Modifier.fillMaxSize()){
        items(imageLinks){
//            Log.d("Image","$baseUrl/data-saver/$hash/$it")
            AsyncImage(model = "$baseUrl/data-saver/$hash/$it", contentDescription = null,Modifier.fillMaxWidth().padding(horizontal = 3.dp,vertical = 8.dp), contentScale = ContentScale.FillWidth)
        }
        
    }


}
@Composable
fun Reader(){


}

