package com.example.mvi_clean_room_hilt_flows.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.example.mvi_clean_room_hilt_flows.domain.Resource
import com.example.mvi_clean_room_hilt_flows.domain.model.MovieInfo
import com.example.mvi_clean_room_hilt_flows.domain.use_case.GetMovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository, pager: Pager<Int, MovieInfoEntity>) : ViewModel() {


    private var _viewState = MutableStateFlow<MovieListState>(MovieListState.Loading)
    private val getMovieInfo = GetMovieInfo(repository)
    // Public immutable state flow for UI observation
    val viewState: StateFlow<MovieListState> = _viewState.asStateFlow()

    fun onAction(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.LoadMovies -> loadMovies()
            is MovieListEvent.LoadMoviesPager -> loadMovies()
            is MovieListEvent.MovieDetails -> loadMovieDetails(event.movieId)
        }
    }
    //TODO do we need to cancel the job when the viewmodel is cleared?
    private var searchJob: Job? = null
    private fun loadMovies() {
        _viewState.value = MovieListState.Loading

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
          getMovieInfo().onEach { result ->
                when(result){
                    is Resource.Success -> _viewState.value = MovieListState.Loaded(result.data)
                    is Resource.Error -> _viewState.value = MovieListState.Error(result.message ?: "An unexpected error occurred")
                    is Resource.Loading -> _viewState.value = MovieListState.Loading
                }
            }.launchIn(this)
        }
    }

    val moviePagingFlow = pager
        .flow
        .map { pagingData ->
            val ps = pagingData.map { it.toMovieInfo() }
            println("pagingData: $ps")
            ps
        }
        .cachedIn(viewModelScope)

    private fun loadMoviesPager() {
        // Load movies from repository
    }

   private fun loadMovieDetails(movieId: Int) {
        // Load movie details from repository
    }
}

sealed class MovieListEvent {
    object LoadMovies : MovieListEvent()
    object LoadMoviesPager : MovieListEvent()
    data class MovieDetails(val movieId: Int) : MovieListEvent()
}

sealed class MovieListState {
    object Loading : MovieListState()
    data class Loaded(val movies: List<MovieInfo>?) : MovieListState()
    data class Error(val message: String) : MovieListState()
}
