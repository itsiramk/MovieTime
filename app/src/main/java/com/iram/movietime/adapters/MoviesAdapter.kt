package com.iram.movietime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.iram.movietime.BuildConfig
import com.iram.movietime.R
import com.iram.movietime.databinding.ItemMovieListBinding
import com.iram.movietime.databinding.ItemMoviesBinding
import com.iram.movietime.db.entity.Movie
import java.util.*

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MovieListViewHolder>() {

    private var items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemDataBinding =
            ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(itemDataBinding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class MovieListViewHolder(private val itemBinding: ItemMoviesBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var itemList: Movie
        fun bind(item: Movie) {
            itemList = item
            val url = BuildConfig.SMALL_IMAGE_URL + item.posterPath
            itemBinding.tvMovieTitle.text = item.originalTitle
            itemBinding.tvMovieReleaseDate.text = item.releaseDate
            Glide.with(itemBinding.root).load(url).transform(CircleCrop())
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.imgMoviePoster)
        }
    }

}