package com.example.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.example.mymovieapp.databinding.ActivityFilmsDetailsBinding
import com.example.mymovieapp.room.Film
import com.example.mymovieapp.viewmodel.MainViewModel
import com.example.mymovieapp.viewmodel.MainViewModelFactory

class FilmsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmsDetailsBinding
    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as FilmApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("id", 0)

        viewModel.getFilmDetails(id)

        viewModel.errorMessage.observe(this){
            binding.tryAgainBtn.visibility = View.VISIBLE
        }
        binding.tryAgainBtn.setOnClickListener {
            viewModel.getFilmDetails(id)
        }
        binding.favoritesBtn.setOnClickListener{
            Toast.makeText(this, "Save ${binding.titleTV.text}", Toast.LENGTH_SHORT).show()
            viewModel.addFilmToFavorite(Film(0,binding.titleTV.text.toString()))
        }
        fillScreen()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    private fun fillScreen() {
        viewModel.movieDetails.observe(this) {
            if (it != null){
                binding.tryAgainBtn.visibility = View.GONE
                binding.ratingAndRealeaseLayout.visibility = View.VISIBLE
                binding.descriptionTitle.visibility = View.VISIBLE
                binding.genreTV.visibility = View.VISIBLE
                binding.favoritesBtn.visibility = View.VISIBLE
            }
            supportActionBar?.title = it.title
            binding.titleTV.text = it.title
            binding.posterImageIM.load("https://image.tmdb.org/t/p/w500/" + it.backdrop_path)
            binding.voteRatingTV.text = it.vote_average.toString()
            binding.descriptionTV.text = it.overview
            binding.releaseDateTV.text = it.release_date
            val filmGenres = mutableListOf<String>()
            for (genre in it.genres) { filmGenres.add(genre.name) }
            if (filmGenres.isNotEmpty()) { binding.setGenres.text = filmGenres.toString() }
        }
    }
}