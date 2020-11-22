package com.iram.movietime.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.model.MovieListResults
import com.iram.movietime.repository.MovieRepository

class MoviesViewModel  @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private lateinit var movieRepo : MovieRepository

    init {
        movieRepo = movieRepository
    }
    val movieListLiveData = movieRepository.getMoviesData()

    fun getQueryData(query:String): LiveData<List<Movie>> {

        return movieRepo.getQueryData(query)
    }

}
