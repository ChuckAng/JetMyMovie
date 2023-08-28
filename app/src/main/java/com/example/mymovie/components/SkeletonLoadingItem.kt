package com.example.mymovie.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkeletonLoadingItem() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val gradientColors = listOf(Color.Transparent, Color.Black)
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            val config = LocalConfiguration.current
            val screenHeight = config.screenHeightDp.dp
            val itemsCount = 8
            val numPages = Int.MAX_VALUE / itemsCount
            val startPage = numPages / 2
            val startIndex = (startPage * itemsCount)
            val pageState = rememberPagerState(initialPage = startIndex)

            val infiniteTransition = rememberInfiniteTransition()
            val alphaAnimation by infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 0.6f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            SkeletonLine(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                    .background(Color.LightGray)
            )
            Box(
                modifier = Modifier
                    .height(screenHeight * 0.65f)
                    .padding(top = 16.dp)
            ) {
                HorizontalPager(
                    pageCount = Int.MAX_VALUE, state = pageState,
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp,
                    userScrollEnabled = false,
                    verticalAlignment = Alignment.Top,
                ) { index ->
                    SkeletonCard(index, pageState)
                }
            }
            Column(modifier = Modifier
                .wrapContentSize()
                .graphicsLayer { alpha = alphaAnimation }) {
                Spacer(modifier = Modifier.height(16.dp))
                SkeletonLine(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                FeaturedSkeletonCard(
                    modifier = Modifier
                        .height(screenHeight * 0.2f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                        .background(Color.LightGray)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(modifier = Modifier
                .wrapContentSize()
                .graphicsLayer { alpha = alphaAnimation }) {
                SkeletonLine(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(userScrollEnabled = false) {
                    items(4) {
                        RegularSkeletonCard()
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                SkeletonLine(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(userScrollEnabled = false) {
                    items(4) {
                        RegularSkeletonCard()
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkeletonCard(page: Int, pageState: PagerState) {
    val pageOffset = ((pageState.currentPage - page) + pageState
        .currentPageOffsetFraction).absoluteValue
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val infiniteTransition = rememberInfiniteTransition()
    val alphaAnimation by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(Color.LightGray)
            .graphicsLayer(alpha = alphaAnimation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.4f)
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(corner = CornerSize(32.dp)))
                .background(Color.Gray)
        )
        Column(modifier = Modifier
            .height(150.dp * (1 - pageOffset))
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(Color.LightGray)
            .graphicsLayer { alpha = 1 - pageOffset }) {
            val descriptionModifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                .background(Color.Gray)
            SkeletonLine(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .padding(start = 32.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SkeletonLine(modifier = descriptionModifier)
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLine(modifier = descriptionModifier)
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLine(modifier = descriptionModifier)
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLine(modifier = descriptionModifier)
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLine(modifier = descriptionModifier)
        }
    }
}

@Composable
fun SkeletonLine(modifier: Modifier) {
    Box(modifier = modifier)
}

@Composable
fun FeaturedSkeletonCard(modifier: Modifier) {
    Box(modifier = modifier)
}

@Composable
fun RegularSkeletonCard() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp * 0.3f
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp * 0.65f
    Box(
        modifier = Modifier
            .height(screenHeight)
            .width(screenWidth)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(Color.LightGray)
    )
}