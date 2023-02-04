package com.example.mymovieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovieapp.model.ConcreteFilm
import com.example.mymovieapp.utils.RetrofitService

class FilmPagingSource(private val retrofitService: RetrofitService, private val endpointType:String):PagingSource<Int, ConcreteFilm>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ConcreteFilm> {
        return try {
            val position = params.key?:3
            val response = retrofitService.getFilms(endpoint = endpointType, page = position)
            val previousKey = if (position==1) null else position-1
            LoadResult.Page(data = response.body()!!.results,
            prevKey = previousKey,
            nextKey = position+1)
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, ConcreteFilm>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}