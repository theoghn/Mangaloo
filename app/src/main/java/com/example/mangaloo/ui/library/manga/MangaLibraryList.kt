package com.example.mangaloo.ui.library.manga

import android.content.res.Resources.Theme
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mangaloo.theme.MangalooTheme
import com.example.mangaloo.ui.library.MangaItem

@Composable
fun MangaLibraryList (){
    Scaffold(topBar = {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp,top = 24.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null,Modifier.size(30.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "My Mangas", fontFamily = FontFamily.SansSerif, fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))

            Icon(imageVector = Icons.Default.List, contentDescription = null,Modifier.size(30.dp))

        }
    }) {it->
        LazyColumn(Modifier.padding(it)){
            item{
                MangaItem(coverImage = "https://uploads.mangadex.org/covers/32d76d19-8a05-4db0-9fc2-e0b0648fe9d0/e90bdc47-c8b9-4df7-b2c0-17641b645ee1.jpg", mangaAuthor = "Chugong", mangaName = "Solo Leveling", mangaStatus = "Ongoing", lastChapter = 200)
                MangaItem(coverImage = "https://uploads.mangadex.org/covers/304ceac3-8cdb-4fe7-acf7-2b6ff7a60613/12158456-0511-468b-be37-8d2aa3772723.png", mangaAuthor = "Isayama Hajime", mangaName = "Attack on Titan", mangaStatus = "Completed", lastChapter = 139)
                MangaItem(coverImage = "https://uploads.mangadex.org/covers/a77742b1-befd-49a4-bff5-1ad4e6b0ef7b/cb980d1e-4d2a-492e-9ca5-399bd21c02b3.jpg", mangaAuthor = "Fujimoto Tatsuki", mangaName = "Chainsaw Man", mangaStatus = "Ongoing", lastChapter = 155)
                MangaItem(coverImage = "https://uploads.mangadex.org/covers/c52b2ce3-7f95-469c-96b0-479524fb7a1a/40dfaef9-0360-4086-b0d2-d655579bf1d0.jpg", mangaAuthor = "Akutami Gege", mangaName = "Jujutsu Kaisen", mangaStatus = "Ongoing", lastChapter = 251)

            }
        }

    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewManga(){
    MangalooTheme(darkTheme = true){
        MangaLibraryList()

    }
}