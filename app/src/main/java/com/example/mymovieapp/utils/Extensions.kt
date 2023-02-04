package com.example.mymovieapp.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import com.example.mymovieapp.FilmsDetailsActivity
import com.example.mymovieapp.paging.FilmPagerAdapter

fun Fragment.loadStateListener(myAdapter: FilmPagerAdapter, progressBar: ProgressBar, button: Button) {
    myAdapter.addLoadStateListener { loadState ->
        if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
            progressBar.visibility = View.VISIBLE
            button.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                button.visibility = View.VISIBLE
            }
        }
    }
}

fun Fragment.navigateToDetailsActivity(context: Context, movieId: Int) {
    val intent = Intent(context, FilmsDetailsActivity::class.java)
    intent.putExtra("id", movieId)
    startActivity(intent)
}