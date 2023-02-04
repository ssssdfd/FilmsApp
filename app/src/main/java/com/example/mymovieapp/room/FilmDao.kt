package com.example.mymovieapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * FROM film_table ORDER by film_title ASC")
    fun getAllFavoriteFilms():Flow<List<Film>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmToDb(film: Film)

    @Query ("DELETE FROM film_table")
    suspend fun deleteAllFilms()

    @Delete
    suspend fun deleteFilm(film: Film)
}