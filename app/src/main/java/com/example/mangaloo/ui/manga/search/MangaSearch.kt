package com.example.mangaloo.ui.manga.search


import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.example.mangaloo.model.api.manga.ApiManga
import com.example.mangaloo.ui.manga.MangaItem

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MangaSearch(viewModel: MangaSearchViewModel, navigate: (String) -> Unit) {
    val response by viewModel.response.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val search: (String,Context) -> Unit = { title,context ->
        viewModel.getManga(title,context)
    }
    val focusManager = LocalFocusManager.current



    Scaffold(topBar = {
        SearchBar(search = search, viewModel)
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })) {
            if (isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else {
                if (response.data.isEmpty()) {
                    NoDataText(it)
                } else {
                    val mangaList = response.data
                    MangaLazyList(
                        mangaList = mangaList,
                        it,
                        navigate = { route: String -> navigate(route) })
                }
            }

        }

    }

}

@Composable
fun MangaLazyList(
    mangaList: List<ApiManga?>,
    paddingValues: PaddingValues,
    navigate: (String) -> Unit
) {
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
                mangaName = manga?.attributes?.title?.en?:manga?.attributes?.title?.jaRo,
                mangaAuthor = manga?.relationships?.get(0)?.attributes?.name,
                navigate = { route: String -> navigate(route) },
                mangaId = mangaId,
            )

        }
    }
}

@Composable
fun NoDataText(paddingValues: PaddingValues) {
    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.8f))
        Text(text = "ಥ_ಥ", fontSize = 50.sp)
        Text(text = "No Manga Found", fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))


    }

}
@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchBar(
    search: (String, Context) -> Unit,
    viewModel: MangaSearchViewModel,
) {
    val context = LocalContext.current
    val searchTitle by viewModel.searchTitle.collectAsState()
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp), // Adjust vertical padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp)),
            value = searchTitle,
            onValueChange = {
                viewModel.updateSearch(it)
            },
            placeholder = { Text(text = "Search") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
            keyboardActions = KeyboardActions(
                onDone = {
                    search(searchTitle,context)
                    focusManager.clearFocus()
                }
            )
        )
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