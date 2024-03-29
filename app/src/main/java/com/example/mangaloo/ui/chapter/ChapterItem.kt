package com.example.mangaloo.ui.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@Composable
fun ChapterItem(
    volume: String?,
    chapter: String?,
    chapterTitle: String?,
    scanlator: String?,
    uploadDate: String,
    openChapter: (String) -> Unit,
    chapterId:String
) {

    val date = LocalDateTime.parse(uploadDate)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable{openChapter(chapterId)}
            .background(Color(0xFF030A1C))
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp).padding(vertical = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (!volume.isNullOrBlank())
                    Text(text = "Vol.${volume} ")
                Text(text = "Ch.${chapter} ")
                if (!chapterTitle.isNullOrBlank())
                    Text(text = "• $chapterTitle", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top=4.dp)) {
                Text(text = "${date.monthValue}/${date.dayOfMonth}/${date.year} ", fontSize = 12.sp)
                if (!scanlator.isNullOrBlank())
                    Text(text = "• $scanlator", fontSize = 12.sp)
            }

        }
    }
}