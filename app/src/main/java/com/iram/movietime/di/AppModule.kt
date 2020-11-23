package com.iram.movietime.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iram.movietime.BuildConfig
import com.iram.movietime.db.AppDatabase
import com.iram.movietime.db.dao.MovieDao
import com.iram.movietime.network.iService
import com.iram.movietime.remote.ServerDataSource
import com.iram.movietime.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(350, TimeUnit.SECONDS)
                .readTimeout(315, TimeUnit.SECONDS)
                .writeTimeout(315, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideService(retrofit: Retrofit): iService = retrofit.create(iService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideTwitterDao(db: AppDatabase) = db.twitterDao()

    @Singleton
    @Provides
    fun provideRepository(serverDataSource: ServerDataSource,
                          localDataSource: MovieDao) =
        MovieRepository(localDataSource,serverDataSource)
}