package com.example.mymovieapp.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FilmListItemBinding
import com.example.mymovieapp.model.ConcreteFilm

class FilmPagerAdapter(private val myItemClickListener: ItemClickListener): PagingDataAdapter<ConcreteFilm, FilmPagerAdapter.ConcreteMovieViewHolder>(MovieComparator) {
    interface ItemClickListener{
        fun onItemClick(clickedMovie:ConcreteFilm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcreteMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_list_item, parent, false)
        return ConcreteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConcreteMovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie!!,myItemClickListener)
    }

   inner class ConcreteMovieViewHolder(item: View): RecyclerView.ViewHolder(item) {
       private val binding = FilmListItemBinding.bind(item)
       fun bind(concreteFilm:ConcreteFilm, myBindItemClickListener: ItemClickListener) = with(binding){
           posterImage.load("https://image.tmdb.org/t/p/w500/"+concreteFilm.poster_path)
           itemView.setOnClickListener { myBindItemClickListener.onItemClick(concreteFilm) }
       }
    }
    object MovieComparator: DiffUtil.ItemCallback<ConcreteFilm>() {
        override fun areItemsTheSame(oldItem: ConcreteFilm, newItem: ConcreteFilm): Boolean {
            return oldItem.original_title == newItem.original_title
        }

        override fun areContentsTheSame(oldItem: ConcreteFilm, newItem: ConcreteFilm): Boolean {
            return oldItem == newItem
        }
    }
}