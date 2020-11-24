package com.iram.movietime.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.repository.MovieRepository
import com.iram.movietime.utils.Resource
import kotlinx.coroutines.Dispatchers

class MoviesViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private lateinit var movieRepo: MovieRepository

    init {
        movieRepo = movieRepository
    }

    val movieListLiveData = movieRepository.getMoviesData()

    fun getQueryData(query: String): LiveData<List<Movie>> {

        return movieRepo.getQueryData(query)
    }

    fun getCreditData(moviedId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = movieRepo.getCreditDetails(moviedId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }

    fun getReviews(moviedId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = movieRepo.getReviews(moviedId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }
     fun getMovieDetails(): LiveData<List<Movie>> {
        return movieRepo.getMovieDetails()
    }
}
