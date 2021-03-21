package com.example.movieappcompose.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.example.movieappcompose.LastUpdated
import com.example.movieappcompose.data.dataSources.api.ApiConsts
import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.db.MoviesDB
import com.example.movieappcompose.data.dataSources.db.dao.GenreDao
import com.example.movieappcompose.data.dataSources.db.dao.MovieDao
import com.example.movieappcompose.data.datastore.Settings
import com.example.movieappcompose.data.datastore.SettingsSerializer
import com.example.movieappcompose.utlis.ConnectivityInterceptor
import com.example.movieappcompose.utlis.MovieApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesMovieApi(): MoviesApi {
        val client = OkHttpClient
                .Builder()
                .addInterceptor(ConnectivityInterceptor())
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
    fun providesMovieDao(moviesDb: MoviesDB): MovieDao = moviesDb.movieDao()

    @Provides
    @Singleton
    fun providesGenreDao(moviesDb: MoviesDB): GenreDao = moviesDb.genreDao()

    @Provides
    @Singleton
    fun providesMovieDataStore(@ApplicationContext context: Context): DataStore<LastUpdated> =
        context.createDataStore(
            fileName = "modie_ds.pb",
            serializer = SettingsSerializer
        )

    @Provides
    @Singleton
    fun providesSettings(movieDataStore: DataStore<LastUpdated>): Settings =
        Settings(movieDataStore)
}