package com.example.mangaloo.ui.manga.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mangaloo.model.api.manga.ApiManga
import com.example.mangaloo.navigation.NavRoutes
import com.example.mangaloo.ui.MangaItem

@Composable
fun MangaSearch(viewModel: MangaSearchViewModel) {
    val response by viewModel.response.collectAsState()

    val search: (String) -> Unit = { title ->
        viewModel.getManga(title)
    }


    Scaffold(topBar = {
        SearchBar(search= search)
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(8.dp, top = 24.dp, bottom = 24.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Default.AccountCircle,
//                contentDescription = null,
//                Modifier.size(30.dp)
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Text(text = "My Mangas", fontFamily = FontFamily.SansSerif, fontSize = 20.sp)
//            Spacer(modifier = Modifier.weight(1f))
//
//            Icon(imageVector = Icons.Default.List, contentDescription = null, Modifier.size(30.dp))
//        }
    }) {
        if (response.data.isEmpty()) {
            NoDataText(it)
        } else {
            val mangaList = response.data
            MangaLazyList(mangaList = mangaList, it)
        }
    }

}

@Composable
fun MangaLazyList(mangaList: List<ApiManga?>, paddingValues: PaddingValues) {
    LazyColumn(Modifier.padding(paddingValues)) {
        items(mangaList) { manga ->
            val mangaId = manga?.id
            val rel = manga?.relationships?.filter { it.type == "cover_art" }
            val coverLink = rel?.get(0)?.attributes?.fileName
            val coverFinalLink =
                if (coverLink == null) "" else "https://uploads.mangadex.org/covers/$mangaId/$coverLink"
            MangaItem(
                coverImage = coverFinalLink,
                lastChapter = manga?.attributes?.lastChapter,
                mangaStatus = manga?.attributes?.status,
                mangaName = manga?.attributes?.title?.en,
                mangaAuthor = manga?.relationships?.get(0)?.attributes?.name
            )

        }
    }
}

@Composable
fun NoDataText(paddingValues: PaddingValues){
    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(0.8f))
        Text(text = "ಥ_ಥ", fontSize = 50.sp)
        Text(text = "No Manga Found", fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))


    }

}
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    search: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = { Text(text = "Search") },
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                search(text.text)
            },
            modifier = Modifier.wrapContentWidth(),
            enabled = text.text.isNotEmpty()
        ) {
            Text(text = "Search")
        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun PreviewManga(){
//    MangalooTheme(darkTheme = true){
//        MangaLibraryList()
//
//    }
//}