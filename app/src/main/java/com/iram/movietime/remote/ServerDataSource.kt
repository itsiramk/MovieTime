package com.iram.movietime.remote

import com.iram.movietime.db.entity.Movie
import com.iram.movietime.model.credits.Credits
import com.iram.movietime.model.movieList.MovieListResults
import com.iram.movietime.model.reviews.Reviews
import com.iram.movietime.network.iService
import retrofit2.Response
import javax.inject.Inject

class ServerDataSource @Inject constructor(
    private val iService: iService
): BaseDataSource() {

    suspend fun getMovieData(apiKey:String) = getResult { iService.getMoviesList(apiKey) }
    suspend fun getCreditDetails(movieId: String, apiKey:String): Credits {
       return iService.getCreditDetails(movieId,apiKey)
    }
    suspend fun getReviews(movieId: String, apiKey:String): Reviews {
       return iService.getReviews(movieId,apiKey)
    }
}
