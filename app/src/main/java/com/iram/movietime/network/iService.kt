package com.iram.movietime.network

import com.iram.movietime.model.credits.Credits
import com.iram.movietime.model.movieList.MovieListResults
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
                                 @Query("api_key") apiKey:String):Credits

}