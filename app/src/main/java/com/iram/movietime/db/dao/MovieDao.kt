package com.iram.movietime.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iram.movietime.db.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(movieList: List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getMovieDetails(): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie where originalTitle LIKE :query")
    fun getQueryData(query: String): LiveData<List<Movie>>
}