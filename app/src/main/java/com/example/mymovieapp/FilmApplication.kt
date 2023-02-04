package com.example.mymovieapp

import android.app.Application
import com.example.mymovieapp.room.FilmDatabase
import com.example.mymovieapp.utils.MainRepository
import com.example.mymovieapp.utils.RetrofitService

class FilmApplication: Application(){
    private val database by lazy { FilmDatabase.getDatabase(this)}
    private val retrofitService by lazy { RetrofitService.getInstance() }
    val repository by lazy {MainRepository(retrofitService, database.filmDao())}
}