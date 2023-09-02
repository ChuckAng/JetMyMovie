package com.example.mymovie.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymovie.R
import com.example.mymovie.components.ExoPlayer
import com.example.mymovie.components.RatingBar
import com.example.mymovie.models.MovieModel
import com.example.mymovie.utils.Utils.Companion.modelFromJson

@Composable
fun MovieDetailScreen(model: String) {
    val movie: MovieModel = modelFromJson(model = model, isHasUrl = true)
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            ExoPlayer(Uri.parse(movie.videoUrl), height = height * 0.33f)
        }
        item {
            Column(
                modifier = Modifier
                    .height(height = height * 0.57f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = movie.title.toString(),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                movie.rating?.let { rating -> RatingBar(rating = rating) }
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.metadata),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))

                MovieDataItem(
                    title = stringResource(R.string.rating_colon),
                    value = "${movie.rating?.div(5)?.times(100)?.toInt()}%"
                )
                MovieDataItem(title = stringResource(R.string.vote_colon), value = "${movie.vote}")
                MovieDataItem(
                    title = stringResource(R.string.duration_colon),
                    value = "${movie.duration}"
                )
                MovieDataItem(
                    title = stringResource(R.string.release_date_colon),
                    value = "${movie.releaseDate}"
                )
                MovieDataItem(
                    title = stringResource(R.string.genre_colon),
                    value = "${movie.genre}"
                )
                MovieDataItem(
                    title = stringResource(R.string.spoken_language_colon),
                    value = "${movie.spokenLanguage}"
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.overview),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.description.toString(),
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.director),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.director.toString(),
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.cast),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.cast.toString(),
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        item {
            Row(modifier = Modifier.height(height * 0.1f)) {
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.price_colon),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp
                    )
                    Text(
                        text = movie.price.toString(),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(CornerSize(16.dp)))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(R.string.buy_ticket),
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Filled.ShoppingBasket,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDataItem(title: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}
