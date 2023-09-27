package com.example.mymovie.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.networking.Resource
import com.example.mymovie.repository.MovieRepository
import com.example.mymovie.states.SearchScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    private val repo by lazy { MovieRepository() }
    private val resultStateFlow =
        MutableStateFlow<SearchScreenState>(SearchScreenState.Idle)
    val result: StateFlow<SearchScreenState> get() = resultStateFlow.asStateFlow()

    fun getMovies(searchKey: String? = null, skip: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            resultStateFlow.emit(SearchScreenState.Loading)
            when (val resource = repo.getMovies(searchKey, skip, 10)) {
                is Resource.Success -> {
                    resource.data.let { data ->
                        resultStateFlow.emit(SearchScreenState.Success(data))
                    }
                }
                is Resource.Error -> {
                    resource.errorMsg?.let { errorMsg ->
                        resultStateFlow.emit(SearchScreenState.Error(errorMsg))
                    }
                }
                is Resource.Exception -> {
                    resultStateFlow.tryEmit(
                        SearchScreenState.Error(
                            resource.errorMsg.toString()
                        )
                    )
                }
            }
        }
    }
}