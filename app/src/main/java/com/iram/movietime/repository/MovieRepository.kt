package com.iram.movietime.repository

import androidx.lifecycle.LiveData
import com.iram.movietime.BuildConfig
import com.iram.movietime.db.dao.MovieDao
import com.iram.movietime.db.entity.Movie
import com.iram.movietime.model.credits.Credits
import com.iram.movietime.remote.ServerDataSource
import com.iram.movietime.remote.fetchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private var serverDataSource: ServerDataSource
) {


    fun getMoviesData() = fetchData(
        databaseQuery = { movieDao.getMovieDetails() },
        networkCall = { serverDataSource.getMovieData(BuildConfig.TMDB_API_KEY) },
        saveCallResult = { movieDao.insertDetails(it.results) }
    )

    suspend fun getCreditDetails(movieId: String) : Credits {
        return serverDataSource.getCreditDetails(movieId, BuildConfig.TMDB_API_KEY)
    }


    fun getQueryData(query: String): LiveData<List<Movie>> {
        return movieDao.getQueryData(query)
    }

}