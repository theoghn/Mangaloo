package com.example.mangaloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mangaloo.ui.manga.search.MangaSearchViewModel
import com.example.mangaloo.theme.MangalooTheme
import com.example.mangaloo.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangalooTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainScreen(navController)
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
