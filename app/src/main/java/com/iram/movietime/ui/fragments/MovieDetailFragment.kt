package com.iram.movietime.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.iram.movietime.adapters.MovieCastAdapter
import com.iram.movietime.databinding.LayoutMoviedetailsBinding
import com.iram.movietime.model.credits.Credits
import com.iram.movietime.utils.Resource
import com.iram.movietime.utils.autoCleared
import com.iram.movietime.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_moviedetails.*


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var movieCastAdapter: MovieCastAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var binding: LayoutMoviedetailsBinding by autoCleared()
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
        arguments?.getInt("id")?.let { fetchActorData(it.toString()) }
        arguments?.getString("name")?.let { collapsing_toolbar.title = it }
    }

    private fun initViews() {
        movieCastAdapter = MovieCastAdapter()
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.moviesContent.rvCast.layoutManager = mLayoutManager
        binding.moviesContent.rvCast.itemAnimator = DefaultItemAnimator()
        binding.moviesContent.rvCast.adapter = movieCastAdapter
    }

    private fun fetchActorData(moviedId: String) {
        moviesViewModel.getCreditData(moviedId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        showProgress(false)
                        resource.data?.let { retrieveList(it) }
                    }
                    Resource.Status.ERROR -> {
                        showProgress(false)
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        showProgress(true)
                    }
                }

            }
        })
    }

    private fun setMovieContents(){

    }

    private fun retrieveList(credits: Credits) {
        if (credits.cast.size > 0) {
            movieCastAdapter.setItems(credits.cast)
        }
    }

    private fun showProgress(status: Boolean) {
        if (status) {
            pBar.visibility = View.VISIBLE
        } else {
            pBar.visibility = View.GONE
        }
    }
}