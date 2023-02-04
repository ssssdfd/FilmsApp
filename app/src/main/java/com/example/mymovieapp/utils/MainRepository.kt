package com.example.mymovieapp.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovieapp.model.ConcreteFilm
import com.example.mymovieapp.model.FilmWithDetails
import com.example.mymovieapp.paging.FilmPagingSource
import com.example.mymovieapp.room.Film
import com.example.mymovieapp.room.FilmDao
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MainRepository (private val retrofitService: RetrofitService, private val filmDao:FilmDao) {
    fun getFilmResponse(endpointType:String):Flow<PagingData<ConcreteFilm>>{
       return Pager(config = PagingConfig(pageSize = 3, enablePlaceholders = false, initialLoadSize = 2),
       pagingSourceFactory = {FilmPagingSource(retrofitService, endpointType)},
       initialKey = 1).flow
    }

    suspend fun getFilmDetails(movieId:Int): Response<FilmWithDetails> {
        return retrofitService.getFilmDetails(id = movieId)
    }

    val allFilms: Flow<List<Film>> = filmDao.getAllFavoriteFilms()

    suspend fun insertFilm(film: Film){
        filmDao.insertFilmToDb(film)
    }

    suspend fun deleteAllFilms(){
        filmDao.deleteAllFilms()
    }

    suspend fun deleteFilm(film: Film){
        filmDao.deleteFilm(film)
    }
}