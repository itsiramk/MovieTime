package com.iram.movietime.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.iram.movietime.R
import com.iram.movietime.adapters.MovieListAdapter
import com.iram.movietime.databinding.LayoutMovielistBinding
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.utils.Resource
import com.iram.movietime.utils.UtilActivity
import com.iram.movietime.utils.autoCleared
import com.iram.movietime.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_movielist.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieListAdapter.MoviesItemListener,SearchView.OnQueryTextListener {

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
        etSearch.setOnQueryTextListener(this)
    }

    private fun initViews() {
        moviesAdapter = MovieListAdapter(this)
        binding.rView.layoutManager = LinearLayoutManager(context)
        binding.rView.setItemAnimator(DefaultItemAnimator())
        binding.rView.adapter = moviesAdapter
        binding.swipeRefresh.setOnRefreshListener {
            fetchMoviesData()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun fetchMoviesData() {
        moviesViewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        filteredData=ArrayList()
                        filteredData = it.data
                        moviesAdapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.pBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedItemData(id: Int,name:String,overview:String) {
        findNavController().navigate(
            R.id.action_moviesListFragment_to_movieDetailFragment,
            bundleOf("id" to id,"name" to name,"overview" to overview)
        )
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        fetchQueryDataFromDb(query)
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        fetchQueryDataFromDb(newText)
        return false
    }
    private fun fetchQueryDataFromDb(query: String) {
        moviesViewModel.getQueryData(query).observe(
            this,
            object : Observer<List<Movie>> {
                override fun onChanged(listData: List<Movie>) {
                    if (listData.isEmpty()) {
                        moviesAdapter.setItems(filteredData as java.util.ArrayList<Movie>)
                    } else {
                        moviesAdapter.setItems(listData as java.util.ArrayList<Movie>)
                    }
                }
            }
        )
    }
}