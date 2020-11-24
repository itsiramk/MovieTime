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
import com.iram.movietime.databinding.ItemMoviecastBinding
import com.iram.movietime.model.credits.Cast
import java.util.ArrayList


class MovieCastAdapter : RecyclerView.Adapter<MovieCastAdapter.MovieCastViewHolder>() {

    private var items = ArrayList<Cast>()

    fun setItems(items: List<Cast>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        val itemDataBinding = ItemMoviecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieCastAdapter.MovieCastViewHolder(itemDataBinding)
    }

    override fun getItemCount(): Int = items.size

    class MovieCastViewHolder(private val itemBinding: ItemMoviecastBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

        private lateinit var itemList:Cast

        fun bind(item: Cast) {
            itemList = item
            itemBinding.tvActorName.text = item.name
            val url = BuildConfig.SMALL_IMAGE_URL + item.profile_path
            Glide.with(itemBinding.root)
                .load(url)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_bookmyshow_logo_vector)
                .error(R.drawable.ic_bookmyshow_logo_vector)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.imgActor)

        }
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) = holder.bind(items[position])

}