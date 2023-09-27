package com.example.mymovie.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mymovie.R
import com.example.mymovie.models.MovieModel
import com.example.mymovie.states.SearchScreenState
import com.example.mymovie.viewModels.SearchScreenViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(viewModel: SearchScreenViewModel = viewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchKey by remember { mutableStateOf("") } // Query for SearchBar
    var searchedResultKey by remember { mutableStateOf("") }
    val eventState = viewModel.result.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.getMovies(skip = 0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, bottom = 120.dp, start = 32.dp, end = 32.dp)
    ) {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerSize(16.dp))),
            query = searchKey,
            onQueryChange = {
                searchKey = it
            },
            onSearch = { key ->
                viewModel.getMovies(key, 0)
                searchedResultKey = key
                keyboardController?.hide()
            },
            active = false,
            onActiveChange = {}, // this will change design of searchBar, no implementation here
            placeholder = {
                Text(text = stringResource(R.string.search_movie_name))
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }) {}
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
        ) {
            when (eventState) {
                is SearchScreenState.Success -> {
                    val movies = eventState.data
                    if (movies.isEmpty()) {
                        item {
                            Image(
                                painter = painterResource(id = R.drawable.empty_state_icon),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = stringResource(R.string.no_result_found, searchedResultKey),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    items(movies.size) { index ->
                        RegularCard(data = movies[index]) {
                        }
                    }
                }
                is SearchScreenState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
                is SearchScreenState.Error -> {
                }
                is SearchScreenState.Idle -> {
                }
            }
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
