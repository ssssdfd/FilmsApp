package com.example.mymovieapp.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.databinding.FavoriteFilmItemBinding

class FavoriteFilmAdapter(private val listener: ClickListener): RecyclerView.Adapter<FavoriteFilmAdapter.FavoriteFilmViewHolder>() {
    var favoriteFilmList = listOf<Film>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFilmViewHolder {
        val binding = FavoriteFilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteFilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteFilmViewHolder, position: Int) {
        val currentFilm = favoriteFilmList[position]
        holder.bind(currentFilm, listener)
    }

    override fun getItemCount() = favoriteFilmList.size

    class FavoriteFilmViewHolder(private val binding:FavoriteFilmItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film, listener: ClickListener) = with(binding){
            setFilmName.text = film.film_title
            itemView.setOnClickListener{
                listener.onClick(film)
            }
        }
    }

    interface ClickListener{
        fun onClick(film: Film)
    }
}





