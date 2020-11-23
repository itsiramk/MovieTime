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
import com.iram.movietime.db.entity.Movie
import java.util.*

class MovieListAdapter(private val listener: MoviesItemListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private var items = ArrayList<Movie>()

    interface MoviesItemListener {
        fun onClickedItemData(id: Int,name: String)
    }

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemDataBinding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(itemDataBinding, listener)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class MovieListViewHolder(
        private val itemBinding: ItemMovieListBinding,
        private val listener: MoviesItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private lateinit var itemList: Movie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Movie) {
            itemList = item
            val url = BuildConfig.SMALL_IMAGE_URL + item.posterPath
            itemBinding.tvMovieTitle.text = item.originalTitle
            itemBinding.tvMovieReleaseDate.text = item.releaseDate
            Glide.with(itemBinding.root)
                .load(url)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.imgMoviePoster)

        }

        override fun onClick(p0: View?) {
            listener.onClickedItemData(itemList.id,itemList.originalTitle)
        }
    }

}