package com.example.mangaloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mangaloo.theme.MangalooTheme
import com.example.mangaloo.ui.chapter.reader.ChapterReader
import com.example.mangaloo.ui.chapter.reader.ChapterReaderViewModel
import com.example.mangaloo.ui.home.Home
import com.example.mangaloo.ui.home.HomeViewModel
import com.example.mangaloo.ui.main.MainScreen
import com.example.mangaloo.ui.main.MainScreenViewModel
import com.example.mangaloo.ui.manga.search.MangaSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            MangalooTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainScreenViewModel = viewModel()
                    MainScreen(viewModel)


                }
            }
        }
    }
}

//@Composable
//fun Greeting(viewModel: TestViewModel) {
//    Column {
//        Text(
//            text = response.response,
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        TextField(
//            value = if(response.data.count()>1)  response.data.count().toString() else "waitin",
//            onValueChange = {}
//
//        )
//    }
//
//}
