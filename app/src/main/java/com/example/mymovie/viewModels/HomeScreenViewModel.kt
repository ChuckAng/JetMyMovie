package com.example.mymovie.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.networking.Resource
import com.example.mymovie.intents.HomeScreenIntent
import com.example.mymovie.repository.MovieRepository
import com.example.mymovie.states.HomeScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val repo by lazy { MovieRepository() }
    val resultChannel = Channel<HomeScreenIntent>()
    private val resultStateFlow =
        MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val result: StateFlow<HomeScreenState> get() = resultStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            resultChannel.consumeAsFlow().collect {
                if (it is HomeScreenIntent.GetMovieEvent) {
                    getMovies()
                }
            }
        }
    }

    private suspend fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val resource = repo.getMovies()) {
                is Resource.Success -> {
                    resource.data.let { data ->
                        resultStateFlow.tryEmit(HomeScreenState.Success(data))
                    }
                }
                is Resource.Error -> {
                    resource.errorMsg?.let { errorMsg ->
                        resultStateFlow.tryEmit(HomeScreenState.Error(errorMsg))
                    }
                }
                is Resource.Exception -> {
                    resultStateFlow.tryEmit(
                        HomeScreenState.Error(
                            resource.errorMsg.toString()
                        )
                    )
                }
            }
        }
    }
}