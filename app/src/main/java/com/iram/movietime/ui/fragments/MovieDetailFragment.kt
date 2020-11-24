package com.iram.movietime.ui.fragments

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.iram.movietime.BuildConfig
import com.iram.movietime.R
import com.iram.movietime.adapters.MovieCastAdapter
import com.iram.movietime.adapters.MoviesAdapter
import com.iram.movietime.databinding.LayoutMoviedetailsBinding
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.model.credits.Credits
import com.iram.movietime.model.reviews.Reviews
import com.iram.movietime.utils.Resource
import com.iram.movietime.utils.UtilActivity
import com.iram.movietime.utils.autoCleared
import com.iram.movietime.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_moviedetails.*
import java.util.*


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var imageUrl: String
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var movieCastAdapter: MovieCastAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var binding: LayoutMoviedetailsBinding by autoCleared()
    private lateinit var movieId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutMoviedetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fetchDataFromIntent()
    }

    private fun initViews() {
        movieCastAdapter = MovieCastAdapter()
        moviesAdapter = MoviesAdapter()
        setupRecyclerView()
        appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.imgPoster.setImageResource(0)
                    collapsing_toolbar.title = getString(R.string.movie_details)
                    collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
                    isShow = true
                } else if (isShow) {
                    collapsing_toolbar.title = ""
                    UtilActivity.loadImage(binding.root, binding.imgPoster, imageUrl)
                    isShow = false
                }
            }
        })
    }
    private fun setupRecyclerView() {
        val mLayoutManager1 = LinearLayoutManager(context)
        val mLayoutManager2 = LinearLayoutManager(context)
        mLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        mLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        binding.moviesContent.rvCast.layoutManager = mLayoutManager1
        binding.moviesContent.rvMovies.layoutManager = mLayoutManager2
        binding.moviesContent.rvCast.itemAnimator = DefaultItemAnimator()
        binding.moviesContent.rvMovies.itemAnimator = DefaultItemAnimator()
        binding.moviesContent.rvCast.adapter = movieCastAdapter
        binding.moviesContent.rvMovies.adapter = moviesAdapter
    }

    private fun fetchDataFromIntent() {
        arguments?.getInt("id")?.let { movieId = it.toString() }
        arguments?.getString("name")?.let { binding.moviesContent.tvTitle.text = it }
        arguments?.getString("overview")?.let { binding.moviesContent.tvMovieOverView.text = it }
        fetchActorData(movieId)
        fetchMovieContents(movieId)
        fetchMovieDataFromDb()
    }

    private fun fetchActorData(moviedId: String) {
        moviesViewModel.getCreditData(moviedId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        UtilActivity.showProgress(pBar, false)
                        resource.data?.let { renderActorData(it) }
                    }
                    Resource.Status.ERROR -> {
                        UtilActivity.showProgress(pBar, false)
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        UtilActivity.showProgress(pBar, true)
                    }
                }
            }
        })
    }

    private fun renderMovieData(movieResult: List<Movie>) {
        imageUrl = BuildConfig.LARGE_IMAGE_URL + movieResult.get(0).posterPath
        UtilActivity.loadImage(binding.root, binding.imgPoster, imageUrl)
        moviesAdapter.setItems(movieResult as ArrayList<Movie>)
    }

    private fun fetchMovieContents(moviedId: String) {
        moviesViewModel.getReviews(moviedId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        UtilActivity.showProgress(pBar, false)
                        resource.data?.let { renderMovieData(it) }
                    }
                    Resource.Status.ERROR -> {
                        UtilActivity.showProgress(pBar, false)
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        UtilActivity.showProgress(pBar, true)
                    }
                }
            }
        })
    }

    fun fetchMovieDataFromDb() {
        moviesViewModel.getMovieDetails().observe(viewLifecycleOwner,
            { movie -> renderMovieData(movie) })
    }

    private fun renderMovieData(reviews: Reviews) {
        if (reviews.results.size > 0) {
            val review = reviews.results[0]
            val rating = context?.resources?.getString(R.string.movie_rating)
            binding.moviesContent.tvMovieRating.text = rating + " " + review.author_details.rating
        }
    }

    private fun renderActorData(credits: Credits) {
        if (credits.cast.isNotEmpty()) {
            movieCastAdapter.setItems(credits.cast)
        }
    }
}