package com.example.movieappcompose.di

import android.content.Context
import com.example.movieappcompose.data.dataSources.api.ApiConsts
import com.example.movieappcompose.data.dataSources.api.MovieApiKeyInterceptor
import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.db.MovieDao
import com.example.movieappcompose.data.dataSources.db.MoviesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesMovieApi(): MoviesApi {
        val client = OkHttpClient
                .Builder()
                .addInterceptor(MovieApiKeyInterceptor())
                .build()

        val retrofit = Retrofit
                .Builder()
                .client(client)
                .baseUrl(ApiConsts.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDB(@ApplicationContext context: Context): MoviesDB =
        MoviesDB.createInstance(context = context)

    @Provides
    @Singleton
    fun providesMovieDb(moviesDb: MoviesDB): MovieDao = moviesDb.movieDao()

}