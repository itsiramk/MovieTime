package com.iram.movietime.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.iram.movietime.adapters.MovieListAdapter
import com.iram.movietime.databinding.LayoutMovielistBinding
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.utils.Resource
import com.iram.movietime.utils.autoCleared
import com.iram.movietime.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var binding: LayoutMovielistBinding by autoCleared()
    private lateinit var moviesAdapter: MovieListAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var filteredData: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutMovielistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fetchMoviesData()
        //setupRecyclerView()
        //setupObservers()
    }

    private fun initViews() {
        moviesAdapter = MovieListAdapter()
        binding.rView.layoutManager = LinearLayoutManager(context)
        binding.rView.setItemAnimator(DefaultItemAnimator())
        binding.rView.adapter = moviesAdapter
        binding.swipeRefresh.setOnRefreshListener {
            fetchMoviesData()
            binding.swipeRefresh.isRefreshing = false
        }
        /* binding.btnSearch.setOnClickListener {
             fetchQueryDataFromDb(binding.etSearch.query.toString())
         }*/
    }

    private fun fetchMoviesData() {
        moviesViewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        filteredData = it.data
                        moviesAdapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR -> {
                    Log.d("TAG>>> ", it.message+"")
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.pBar.visibility = View.VISIBLE
            }
        })
    }

}