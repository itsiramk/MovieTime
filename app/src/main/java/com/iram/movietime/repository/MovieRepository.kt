package com.iram.movietime.repository

import androidx.lifecycle.LiveData
import com.iram.movietime.db.dao.MovieDao
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.remote.ServerDataSource
import com.iram.movietime.remote.fetchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private var serverDataSource: ServerDataSource
) {
    private val TMDB_API_KEY: String = "21a8a9e1fd3b311f903cf4a88085e9ca"

    fun getMoviesData() = fetchData(
        databaseQuery = { movieDao.getMovieDetails() },
        networkCall = { serverDataSource.getMovieData(TMDB_API_KEY) },
        saveCallResult = { movieDao.insertDetails(it.results) }
    )

    fun getQueryData(query: String): LiveData<List<Movie>> {
        return movieDao.getQueryData(query)
    }

}