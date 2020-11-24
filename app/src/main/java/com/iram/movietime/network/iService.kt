package com.iram.movietime.network

import com.iram.movietime.db.entity.Movie
import com.iram.movietime.model.credits.Credits
import com.iram.movietime.model.movieList.MovieListResults
import com.iram.movietime.model.reviews.Reviews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface iService
{

    @GET("movie/now_playing")
    suspend fun getMoviesList(@Query("api_key")apiKey:String): Response<MovieListResults>

    @GET("movie/{id}/credits")
    suspend fun getCreditDetails(@Path("id") id: String,
                                 @Query("api_key") apiKey:String): Credits

    @GET("movie/{id}/reviews")
    suspend fun getReviews(@Path("id") id: String,
                                 @Query("api_key") apiKey:String): Reviews

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(@Path("id") id: String,
                                 @Query("api_key") apiKey:String):MovieListResults

}