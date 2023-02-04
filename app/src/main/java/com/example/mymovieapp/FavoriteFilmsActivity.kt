package com.example.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.databinding.ActivityFavoriteFilmsBinding
import com.example.mymovieapp.room.FavoriteFilmAdapter
import com.example.mymovieapp.room.Film
import com.example.mymovieapp.viewmodel.MainViewModel
import com.example.mymovieapp.viewmodel.MainViewModelFactory

class FavoriteFilmsActivity : AppCompatActivity(), FavoriteFilmAdapter.ClickListener {
    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as FilmApplication).repository)
    }
    private lateinit var binding: ActivityFavoriteFilmsBinding
    private  var favoriteFilmAdapter =  FavoriteFilmAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteFilmsBinding.inflate(layoutInflater)
        supportActionBar?.title = "Your Favorite Films"
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getAllFilms.observe(this){
            favoriteFilmAdapter.favoriteFilmList = it
            binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.favoriteRecyclerView.adapter = favoriteFilmAdapter
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemDelete->{ deleteUser() }
            android.R.id.home->{ finish() }
        }
        return true
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){_,_-> viewModel.deleteAllFilmsFromFavorite() }
        builder.setNegativeButton("No"){_,_-> }
        builder.setTitle("Delete all films?")
        builder.setMessage("Are you sure you want to delete all films?")
        builder.create().show()
    }

    override fun onClick(film: Film) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){_,_-> viewModel.deleteCurrentFilm(film) }
        builder.setNegativeButton("No"){_,_-> }
        builder.setTitle("Delete ${film.film_title} ?")
        builder.setMessage("Are you sure you want to delete ${film.film_title}?")
        builder.create().show()

    }


}