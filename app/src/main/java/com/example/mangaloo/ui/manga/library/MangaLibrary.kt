package com.example.mangaloo.ui.manga.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangaloo.ui.home.TopBarTitle
import com.example.mangaloo.ui.manga.MangaLibraryCard

@Composable
fun MangaLibrary(viewModel: MangaLibraryViewModel = hiltViewModel(),navigate: (String) -> Unit) {
    val mangas by viewModel.mangas.collectAsState()

    viewModel.updateList()
    
    Scaffold(topBar = {
        Column {
            TopBarTitle("Your Library")
            Search(viewModel = viewModel)
        }

    }) {
        Box(modifier = Modifier.padding(it)){
            if(mangas.isEmpty()){
                NoMangaText()
            }
            else{
                LazyVerticalGrid(columns = GridCells.Fixed(2),Modifier.padding(6.dp)){
                    items(mangas){
                            manga -> MangaLibraryCard(title = manga.title, cover =manga.coverUrl , mangaId = manga.id , navigate = { route: String -> navigate(route)})
                    }
                }
            }

        }

    }

}

@Composable
fun NoMangaText() {
    Column(
        Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.8f))
        Text(text = "⚆ _ ⚆", fontSize = 50.sp)
        Text(text = "Library is empty", fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))


    }

}
    

@Composable
fun Search(viewModel: MangaLibraryViewModel){
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
            placeholder = { Text(text = "Find in library") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}