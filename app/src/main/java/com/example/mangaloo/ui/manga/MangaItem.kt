package com.example.mangaloo.ui.manga

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mangaloo.navigation.NavRoutes

@Composable
fun MangaItem(
    coverImage: String?,
    mangaId:String?,
    mangaName: String?,
    mangaAuthor: String?,
    mangaStatus: String?,
    lastChapter: String?,
    navigate: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .shadow2(color = Color.Black, borderRadius = 20.dp, blurRadius = 13.dp)
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.DarkGray)
            .clickable {navigate(NavRoutes.chapterList.route+"/$mangaId")  }
    ) {
        Row {
            AsyncImage(
                model = coverImage,
                contentDescription = "cover",
                Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxHeight()
                    .width(90.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    text = "$mangaName",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f))
                Text(text = "by $mangaAuthor", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                Text(
                    text = "$mangaStatus ${if (lastChapter != ""&& lastChapter!= null) ": $lastChapter" else ""}",
                    color = if (mangaStatus == "ongoing") Color(0xFFFF6905) else Color(0xFF2C5F2D),
                    modifier = Modifier
                        .padding(bottom = 20.dp, start = 8.dp)
                        .background(
                            if (mangaStatus == "ongoing") Color(0xFFFFEBF0) else Color(0xFF97BC62),
                            RoundedCornerShape(30.dp)
                        )
                        .padding(start = 5.dp, end = 5.dp),
                )
            }

        }
    }

}
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewManga() {
//    MangalooTheme(darkTheme = true) {
//        MangaItem("https://uploads.mangadex.org/covers/32d76d19-8a05-4db0-9fc2-e0b0648fe9d0/e90bdc47-c8b9-4df7-b2c0-17641b645ee1.jpg.512.jpg")
//    }
//}

fun Modifier.shadow2(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)