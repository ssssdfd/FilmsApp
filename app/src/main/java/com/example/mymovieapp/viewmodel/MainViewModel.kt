package com.example.mymovieapp.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.model.ConcreteFilm
import com.example.mymovieapp.model.FilmWithDetails
import com.example.mymovieapp.room.Film
import com.example.mymovieapp.utils.MainRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {
    val movieDetails = MutableLiveData<FilmWithDetails>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val errorMessage = MutableLiveData<String>()

    fun getFilmsList(endpoint:String):Flow<PagingData<ConcreteFilm>>{
        return mainRepository.getFilmResponse(endpoint).cachedIn(viewModelScope)
    }


    fun getFilmDetails(myId:Int){
        viewModelScope.launch(exceptionHandler) {
            val response: Response<FilmWithDetails> = mainRepository.getFilmDetails(myId)
            if (response.isSuccessful){
                movieDetails.postValue(response.body())
            }else{
                onError("Error : ${response.message()} ")
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    val getAllFilms = mainRepository.allFilms.asLiveData()

    fun addFilmToFavorite(film: Film){
        viewModelScope.launch(exceptionHandler) {
            mainRepository.insertFilm(film)
        }
    }

    fun deleteAllFilmsFromFavorite(){
        viewModelScope.launch(exceptionHandler) {
            mainRepository.deleteAllFilms()
        }
    }

    fun deleteCurrentFilm(film: Film){
        viewModelScope.launch(exceptionHandler) {
            mainRepository.deleteFilm(film)
        }
    }

}