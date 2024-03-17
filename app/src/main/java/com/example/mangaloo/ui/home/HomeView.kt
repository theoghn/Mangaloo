package com.example.mangaloo.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.mangaloo.R
import com.example.mangaloo.navigation.NavRoutes
import com.example.mangaloo.theme.MangalooTheme
import com.example.mangaloo.ui.manga.MangaItem
import kotlin.math.absoluteValue


@Composable
fun Home(viewModel: HomeViewModel, navigate: (String) -> Unit) {
    Scaffold(topBar = { TopBarTitle("Discover") }) {
        Column(Modifier.padding(it)) {
            Recommendation { route: String -> navigate(route) }
            Buttons(viewModel)
            BestRated(viewModel) { route: String -> navigate(route) }
        }
    }
}

@Composable
fun TopBarTitle(title:String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 14.dp,end = 14.dp, bottom = 18.dp,top = 2.dp)
    ) {
        Text(
            title, fontSize = 28.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily(
                Font(R.font.nunito_bold)
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Recommendation(navigate: (String) -> Unit) {
//    hardcoded my recommendation
    val sl =
        "https://mangadex.org/covers/32d76d19-8a05-4db0-9fc2-e0b0648fe9d0/e90bdc47-c8b9-4df7-b2c0-17641b645ee1.jpg.512.jpg"
    val chainsaw =
        "https://mangadex.org/covers/a77742b1-befd-49a4-bff5-1ad4e6b0ef7b/cb980d1e-4d2a-492e-9ca5-399bd21c02b3.jpg.512.jpg"
    val juju =
        "https://mangadex.org/covers/c52b2ce3-7f95-469c-96b0-479524fb7a1a/40dfaef9-0360-4086-b0d2-d655579bf1d0.jpg.512.jpg"
    val onePunch =
        "https://mangadex.org/covers/d8a959f7-648e-4c8d-8f23-f1f3f8e129f3/c6da6ed5-33c0-4c3a-8591-b1c41510d5a6.jpg.512.jpg"
    val ids = listOf(
        "32d76d19-8a05-4db0-9fc2-e0b0648fe9d0",
        "a77742b1-befd-49a4-bff5-1ad4e6b0ef7b",
        "c52b2ce3-7f95-469c-96b0-479524fb7a1a",
        "d8a959f7-648e-4c8d-8f23-f1f3f8e129f3"
    )
    val recommended = listOf(sl, chainsaw, juju, onePunch)
    val pagerState = rememberPagerState(pageCount = { recommended.count() }, initialPage = 1)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 0.55f // Adjust as needed
    val horizontalPadding = (screenWidth - itemWidth) / 2


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            verticalAlignment = Alignment.CenterVertically,
            state = pagerState,
            pageSize = PageSize.Fixed(itemWidth),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            beyondBoundsPageCount = 2
        ) { page ->
            Card(
                Modifier
                    .clickable { navigate(NavRoutes.chapterList.route + "/${ids[page]}") }
                    .fillMaxWidth()
                    .fillMaxHeight(0.44f)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
//                        .background(Color.Red)
                ) {
                    AsyncImage(
                        model = recommended[page],
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
    }


}

@Composable
fun BestRated(viewModel: HomeViewModel, navigate: (String) -> Unit) {
    val mostPopular by viewModel.popularManga.collectAsState()
    val bestRated by viewModel.bestRatedManga.collectAsState()
    val selectedIndex by viewModel.selectedIndex.collectAsState()
    val scrollState = rememberLazyListState()

    val show = if (selectedIndex == 1) bestRated else mostPopular

    LaunchedEffect(selectedIndex) {
        scrollState.scrollToItem(0)
    }

    LazyColumn(state = scrollState) {
        items(show) { manga ->
            val mangaId = manga?.id
            val rel = manga?.relationships?.filter { it.type == "cover_art" }
            val coverLink = rel?.get(0)?.attributes?.fileName
            val coverFinalLink =
                if (coverLink == null) "" else "https://uploads.mangadex.org/covers/$mangaId/$coverLink"
            MangaItem(
                coverImage = coverFinalLink,
                lastChapter = manga?.attributes?.lastChapter,
                mangaStatus = manga?.attributes?.status,
                mangaName = manga?.attributes?.title?.en ?: manga?.attributes?.title?.jaRo,
                mangaAuthor = manga?.relationships?.get(0)?.attributes?.name,
                navigate = { route: String -> navigate(route) },
                mangaId = mangaId
            )

        }
    }
}

@Composable
fun Buttons(viewModel: HomeViewModel) {
    val selectedIndex by viewModel.selectedIndex.collectAsState()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        ElevatedButton(
            onClick = { viewModel.changeIndex(0) },
            Modifier.padding(end = 12.dp),
            colors = if (selectedIndex == 0) ButtonDefaults.buttonColors(containerColor = Color(0xFF5B8FB9))
            else ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF061831))
        ) {
            Text("Popular", color = Color.White)
        }
        ElevatedButton(

            onClick = { viewModel.changeIndex(1) },
            colors = if (selectedIndex == 1) ButtonDefaults.buttonColors(containerColor = Color(0xFF5B8FB9))
            else ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF061831))
        ) {
            Text("Best Rated", color = Color.White )
        }
    }

}

//@Composable
//@Preview
//fun HomePreview() {
//    MangalooTheme {
//        Home()
//    }
//}