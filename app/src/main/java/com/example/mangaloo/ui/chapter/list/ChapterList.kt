package com.example.mangaloo.ui.chapter.list

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mangaloo.R
import com.example.mangaloo.model.db.Manga
import com.example.mangaloo.ui.chapter.ChapterItem
import java.text.DecimalFormat


@Composable
fun ChapterList(
    viewModel: ChapterListViewModel,
    goBack: () -> Boolean,
    openChapter: (String) -> Unit
) {
    val chapterList by viewModel.chapters.collectAsState()
    val manga by viewModel.manga.collectAsState()
    val coverLink by viewModel.coverLink.collectAsState()
    val follows by viewModel.follows.collectAsState()
    val rating by viewModel.rating.collectAsState()


    val mangaName = manga?.attributes?.title?.en ?: manga?.attributes?.title?.jaRo
    val mangaAuthor = manga?.relationships?.get(0)?.attributes?.name
    val description = manga?.attributes?.description?.en

    TopImage(coverLink)

    val dbManga = manga?.let {
        Manga(
            it.id,
            mangaName,
            description,
            it.attributes.lastChapter,
            it.attributes.status,
            coverLink,
            rating.toDouble(),
            follows.toDouble(),
            mangaAuthor
        )
    }

    LazyColumn(Modifier.fillMaxSize()) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .background(Color.Transparent)
            )
            DetailsSection(mangaName, description = description, mangaAuthor, follows, rating)
        }
        items(chapterList) { ch ->
            val scanlationGroup = ch.relationships.filter { it.type == "scanlation_group" }
            Log.d("sada", "${scanlationGroup[0].attributes.name}")
            ChapterItem(
                volume = ch.attributes.volume,
                chapter = ch.attributes.chapter,
                chapterTitle = ch.attributes.title,
                scanlator = if (scanlationGroup.count() > 0) "${scanlationGroup[0].attributes.name}" else "",
                uploadDate = ch.attributes.createdAt.substringBefore("+"),
                openChapter = { chapterId: String -> openChapter(chapterId) },
                chapterId = ch.id
            )
        }

    }
    val saveManga: (Manga) -> Unit = { m ->
        viewModel.saveManga(m)
    }
    val isSaved by viewModel.isSaved.collectAsState()
    TopBar(title = mangaName, goBack = { goBack() },
        saveManga = {if (dbManga != null) { viewModel.saveManga(dbManga) } },
        deleteManga = {if(isSaved){
            manga?.id?.let { viewModel.deleteManga(it) }
        } },
        isSaved = isSaved)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String?,
    goBack: () -> Boolean,
    saveManga: () -> Unit,
    isSaved: Boolean,
    deleteManga: () -> Unit
) {
    TopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {
            IconButton(onClick = { goBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$title", fontWeight = FontWeight.W800, fontSize = 24.sp)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier,
                    onClick = { if (!isSaved) saveManga() else deleteManga() }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = if (!isSaved) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                        contentDescription = "Drop-Down Arrow"
                    )

                }
            }

        }
    )
}

@Composable
fun TopImage(image: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            alpha = 0.6f
        )

    }
}
//animateDpAsState(
//targetValue = targetHeight,
//animationSpec = tween(durationMillis = 500), label = ""
//)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsSection(
    title: String?,
    description: String?,
    author: String?,
    follow: Number,
    rating: Number
) {
    val df = DecimalFormat("#.##")
    df.maximumFractionDigits = 2
    val roundedNumber = df.format(rating)

    val following =
        if (follow.toInt() > 1000) (follow.toInt() / 1000).toString() else follow.toString()
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF030A1C)),
        modifier =
        Modifier
            .animateContentSize()
            .fillMaxWidth()
            .then(if (expandedState) Modifier else Modifier.height(180.dp)),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
        ) {

            TitleStats(title = "$title", rating = roundedNumber, follow = following)

//            Author
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Star",
                    Modifier.size(18.dp)
                )
                Text(text = "$author", fontSize = 12.sp, modifier = Modifier.padding())
            }
//            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter){
            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .then(if (expandedState) Modifier else Modifier.fadingEdge()),

                text = "$description",
                maxLines = if (expandedState) 99 else 3,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationState),
                onClick = {
                    expandedState = !expandedState
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.TwoTone.KeyboardArrowDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }

//            }

        }

    }

}

@Composable
fun TitleStats(title: String, rating: String, follow: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(start = 14.dp, top = 8.dp)
                .fillMaxWidth(0.6f),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.star2),
            contentDescription = "Star",
            Modifier.size(30.dp),
            tint = Color.White
        )
        Text(
            text = rating,
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.bookmark2),
            contentDescription = "Star",
            Modifier.size(30.dp)
        )
        Text(
            text = "${follow}k",
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
        )
    }
}

fun Modifier.fadingEdge() = this.drawWithContent {
    drawContent()
    drawRect(
        brush = Brush.verticalGradient(0.6f to Color.Black, 1f to Color.Transparent),
        blendMode = BlendMode.DstIn
    )
}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewManga() {
//    MangalooTheme(darkTheme = true) {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            ChapterList()
//
//        }
//
//    }
//}