package com.example.mangaloo.ui.chapter

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mangaloo.theme.MangalooTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterList() {
    val title = "Solo Leveling"
//    val image =
//        "https://mangadex.org/covers/32d76d19-8a05-4db0-9fc2-e0b0648fe9d0/e90bdc47-c8b9-4df7-b2c0-17641b645ee1.jpg.512.jpg"
    val image =
        "https://mangadex.org/covers/a77742b1-befd-49a4-bff5-1ad4e6b0ef7b/cb980d1e-4d2a-492e-9ca5-399bd21c02b3.jpg.512.jpg"
    val description =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit,ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat.ullamco laboris nisi ut aliquip ex ea commodo consequat."
    TopImage(image)

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .background(Color.Transparent)
            )
            DetailsSection(title,description = description)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF0F0A20))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF0F0A20))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(800.dp)
                    .background(Color.LightGray)
            )

        }
    }
    TopBar(title = title)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.TwoTone.ArrowBack,
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
                Text(text = title, fontWeight = FontWeight.W800, fontSize = 24.sp)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(modifier = Modifier, onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Outlined.FavoriteBorder,
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
fun DetailsSection(title:String,description: String) {
    val initialFll="85812"
    val rating = "8.94"
    val follow = if(initialFll.toInt()>1000) initialFll.toInt()/1000 else initialFll
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0A20)),
        modifier =
        Modifier
            .animateContentSize()
            .fillMaxWidth()
            .then(if (expandedState) Modifier else Modifier.height(150.dp)),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier= Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, fontSize = 20.sp,modifier= Modifier.padding(8.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = rating, fontSize =14.sp,modifier= Modifier.padding(8.dp))
                Icon(imageVector = Icons.Outlined.Star, contentDescription = "Star" )
                Text(text = "${follow}k", fontSize = 14.sp,modifier= Modifier.padding(8.dp))
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Star" )


            }

            Text(
                modifier = Modifier
                    .padding(start = 7.dp, top = 7.dp),
                text = description,
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
        }

    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewManga() {
    MangalooTheme(darkTheme = true) {
        ChapterList()

    }
}