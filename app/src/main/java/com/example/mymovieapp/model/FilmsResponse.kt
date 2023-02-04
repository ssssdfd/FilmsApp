package com.example.mymovieapp.model

data class FilmsResponse(
    val page: Int,
    val results: List<ConcreteFilm>,
    val total_pages: Int,
    val total_results: Int
)