package com.example.mymovie.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mymovie.R
import com.example.mymovie.components.SkeletonLoadingItem
import com.example.mymovie.intents.HomeScreenIntent
import com.example.mymovie.models.MovieModel
import com.example.mymovie.navigation.Destination
import com.example.mymovie.states.HomeScreenState
import com.example.mymovie.utils.Utils.Companion.modelToJson
import com.example.mymovie.viewModels.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController) {
    val homeScreenViewModel: HomeScreenViewModel = viewModel()
    val eventState = homeScreenViewModel.result.collectAsState()
    val movies = eventState.value
    LaunchedEffect(Unit) {
        homeScreenViewModel.resultChannel.send(HomeScreenIntent.GetMovieEvent)
    }
    when (movies) {
        is HomeScreenState.Loading -> {
            SkeletonLoadingItem()
        }
        is HomeScreenState.Success -> {
            val messageList = movies.data
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val gradientColors = listOf(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                    MaterialTheme.colorScheme.background
                )
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            gradientColors,
                            startY = 0f,
                            endY = size.height / 2
                        ),
                        alpha = 1f
                    )
                }
                HeaderView(messageList = messageList, navController = navController)
            }
        }
        is HomeScreenState.Error -> {
            SkeletonLoadingItem()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HeaderView(messageList: List<MovieModel>, navController: NavController) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        val config = LocalConfiguration.current
        val screenHeights = config.screenHeightDp.dp * 0.6f
        val itemsCount = messageList.size
        val numPages = Int.MAX_VALUE / itemsCount
        val startPage = numPages / 2
        val startIndex = (startPage * itemsCount)
        val pageState = rememberPagerState(initialPage = startIndex)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hello Chuck,",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.wrapContentSize()
                )
                Text(
                    text = stringResource(R.string.book_your_movie_now),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.wrapContentSize()
                )
            }
            Image(
                painter = rememberAsyncImagePainter(messageList[0].imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 56.dp, height = 56.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HeaderTitleCard(title = stringResource(R.string.trending), isShowArrow = false)
        Box(modifier = Modifier.height(screenHeights)) {
            HorizontalPager(
                pageCount = Int.MAX_VALUE, state = pageState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 16.dp,
                verticalAlignment = Alignment.Top,
            ) { index ->
                val page = index % itemsCount
                CarouselCard(
                    data = messageList[page],
                    onClick = {
                        val model = messageList[page]
                        val movie = modelToJson(model, isHasUrl = true)
                        navController.navigate(Destination.MovieDetailScreen.route + "/$movie")
                    },
                    pagerState = pageState,
                    page = index
                )
            }
            var key by remember { mutableStateOf(false) }
            LaunchedEffect(key) {
                delay(3000)
                pageState.animateScrollToPage(
                    page = pageState.currentPage + 1,
                )
                key = !key
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            HeaderTitleCard(title = stringResource(R.string.featured), isShowArrow = false)
            FeaturedCard(data = messageList[0]) {

            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        HeaderTitleCard(title = stringResource(R.string.popular), isShowArrow = true)
        LazyRow {
            items(messageList.size) { index ->
                RegularCard(data = messageList.get(index)) {
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        HeaderTitleCard(title = stringResource(R.string.top_10_in_ms), isShowArrow = true)
        LazyRow {
            items(messageList.size) { index ->
                RegularCard(data = messageList.get(index)) {
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CarouselCard(data: MovieModel, onClick: () -> Unit, pagerState: PagerState, page: Int) {
    val pageOffset = ((pagerState.currentPage - page) + pagerState
        .currentPageOffsetFraction).absoluteValue
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(MaterialTheme.colorScheme.onTertiaryContainer)
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(data.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 16.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .graphicsLayer {
                        val scale = lerp(1f, 1.75f, pageOffset)
                        scaleX *= scale
                        scaleY *= scale
                    },
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .height(150.dp * (1 - pageOffset))
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                    .background(MaterialTheme.colorScheme.onTertiaryContainer)
                    .graphicsLayer { alpha = 1 - pageOffset },
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.title.toString(),
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
                )
                Text(
                    text = data.description.toString(),
                    color = MaterialTheme.colorScheme.onTertiary,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun FeaturedCard(data: MovieModel, onClick: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp * 0.2f
    Box(modifier = Modifier
        .height(screenHeight)
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        .clickable { onClick() }) {
        Image(
            painter = rememberAsyncImagePainter(data.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun HeaderTitleCard(title: String, isShowArrow: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        )
        if (isShowArrow) {
            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun RegularCard(data: MovieModel, onClick: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp * 0.3f
    Box(modifier = Modifier
        .height(screenHeight)
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        .clickable { onClick() }) {
        Image(
            painter = rememberAsyncImagePainter(data.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}