package com.example.mangaloo.ui.manga

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mangaloo.navigation.NavRoutes
import com.example.mangaloo.ui.chapter.list.fadingEdge


@Composable
fun MangaLibraryCard(title: String?, cover: String?, navigate: (String) -> Unit,mangaId:String) {
    Card(
        modifier = Modifier
            .height(290.dp)
            .padding(8.dp)
            .clickable{ navigate(NavRoutes.chapterList.route + "/$mangaId") },
        shape = RoundedCornerShape(6.dp),

    ) {
        Box {
            AsyncImage(model = cover, contentDescription = null,contentScale = ContentScale.Crop,modifier = Modifier.fadingEdge(0.7f,1f))
            Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$title",
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(8.dp),
                    maxLines = 2,
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    val title = "One Piece"
    val photo =
        "https://mangadex.org/covers/32d76d19-8a05-4db0-9fc2-e0b0648fe9d0/e90bdc47-c8b9-4df7-b2c0-17641b645ee1.jpg.512.jpg"

}