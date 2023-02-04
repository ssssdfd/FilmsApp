package com.example.mymovieapp.utils

import com.example.mymovieapp.model.FilmWithDetails
import com.example.mymovieapp.model.FilmsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "1e4cfeb63b0fa033bcfac0579f1d6341"

interface RetrofitService {

    @GET("3/movie/{movie_id}")
    suspend fun getFilmDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String = API_KEY
    ): Response<FilmWithDetails>

    @GET("3/movie/{my_endpoint}")
    suspend fun getFilms(
        @Path("my_endpoint") endpoint:String,
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ):Response<FilmsResponse>

    companion object {
        private var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}