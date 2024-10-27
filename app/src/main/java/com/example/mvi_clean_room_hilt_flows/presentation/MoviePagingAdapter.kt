package com.example.mvi_clean_room_hilt_flows.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi_clean_room_hilt_flows.R
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity


// Replace 'MovieInfoEntity' with your actual data model class
class MoviePagingAdapter : PagingDataAdapter<MovieInfoEntity, MoviePagingAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    // Define the ViewHolder to bind data to the views
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieInfoEntity) {
            //titleTextView.text = movie.title  // Adjust based on MovieInfoEntity fields
        }
    }

    // Define the DiffUtil to efficiently manage changes in the list
    class MovieDiffCallback : DiffUtil.ItemCallback<MovieInfoEntity>() {
        override fun areItemsTheSame(oldItem: MovieInfoEntity, newItem: MovieInfoEntity): Boolean {
            return oldItem.id == newItem.id  // Compare unique ID fields
        }

        override fun areContentsTheSame(
            oldItem: MovieInfoEntity,
            newItem: MovieInfoEntity
        ): Boolean {
            return oldItem == newItem  // Compare the entire object for content equality
        }
    }
}
