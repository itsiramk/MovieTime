package com.iram.movietime.remote

import com.iram.movietime.network.iService
import com.iram.movietime.remote.BaseDataSource
import javax.inject.Inject

class ServerDataSource @Inject constructor(
    private val iService: iService
): BaseDataSource() {

    suspend fun getMovieData(apiKey:String) = getResult { iService.getMoviesList(apiKey) }
}
