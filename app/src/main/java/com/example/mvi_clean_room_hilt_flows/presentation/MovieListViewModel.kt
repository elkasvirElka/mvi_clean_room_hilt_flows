package com.example.mvi_clean_room_hilt_flows.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.example.mvi_clean_room_hilt_flows.domain.entity.MovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private var _viewState = MutableStateFlow<MovieListState>(MovieListState.Loading)

    // Public immutable state flow for UI observation
    val viewState: StateFlow<MovieListState> = _viewState.asStateFlow()

    fun onAction(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.LoadMovies -> loadMovies()
            is MovieListEvent.MovieDetails -> loadMovieDetails(event.movieId)
        }
    }

    private fun loadMovies() {
        _viewState.value = MovieListState.Loading
        viewModelScope.launch {
            repository.getMovies().let { response ->
                when(response.code()) {
                    200 -> {
                        _viewState.value = MovieListState.Loaded(response.body()?.results)
                        //The ETag is server-managed, and if the server doesn't send a new ETag, the client will continue to use the same cached data.
                        println("cache: eTag:".plus(response.headers()["ETag"]))
                    }

                    else -> {
                        _viewState.value = MovieListState.Error("Error loading movies")
                    }
                }
            }
        }
    }

   private fun loadMovieDetails(movieId: Int) {
        // Load movie details from repository
    }
}

sealed class MovieListEvent {
    object LoadMovies : MovieListEvent()
    data class MovieDetails(val movieId: Int) : MovieListEvent()
}

sealed class MovieListState {
    object Loading : MovieListState()
    data class Loaded(val movies: List<MovieInfo>?) : MovieListState()
    data class Error(val message: String) : MovieListState()
}
