package com.example.mvi_clean_room_hilt_flows.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi_clean_room_hilt_flows.R
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    //in case we need repository here (not the best idea, but this is how to do it)
    //private val repository by inject<MovieRepository>()
    //if you use by activityViewModel you get shared ViewModel and only one instance for fragments in the same activity.
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate a ComposeView and set the content to your composable
        return ComposeView(requireContext()).apply {
            setContent {
                MovieListScreen() // Call your composable here
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         lifecycleScope.launch {
             viewModel.viewState.collectLatest { state ->
                 when (state) {
                     is MovieListState.Loading -> {
                         // Show loading
                     }
                     is MovieListState.Loaded -> {
                         state.movies?.forEach {
                             println(it.title)
                         }
                         //MovieListScreen(state.movies)
                     // Show movies
                     }
                     is MovieListState.Error -> {
                         // Show error
                     }
                 }
             }
         }
         lifecycleScope.launch {
             repeat(7) {
                 viewModel.onAction(MovieListEvent.LoadMovies)
                 delay(2000L)
             }
         }
    }

    fun adapter() {
//TODO implement this
        val adapter = MoviePagingAdapter()

        // Setup RecyclerView with the PagingDataAdapter
        //recyclerView.adapter = adapter

        // Observe the flow of paged data
        lifecycleScope.launch {
            //diff with collect is that collectLatest cancels block execution when a new emition is collected
            viewModel.moviePagingFlow.collectLatest {
                adapter.submitData(it.map { it.toMovieInfoEntity() })
            }
        }
    }
    @Composable
    fun MovieListScreen(text: List<String> = listOf()) {
        // Placeholder for the actual screen content
        Text(text = text.joinToString { it + "\n" })
    }
}
