package com.example.movieappcompose.di

import com.example.movieappcompose.data.dataSources.api.MovieApiKeyInterceptor
import com.example.movieappcompose.data.dataSources.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        return retrofit.create(MoviesApi::class.java)
    }

}