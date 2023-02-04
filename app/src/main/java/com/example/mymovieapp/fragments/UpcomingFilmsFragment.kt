package com.example.mymovieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.FilmApplication
import com.example.mymovieapp.databinding.FragmentUpcomingFilmsBinding
import com.example.mymovieapp.utils.loadStateListener
import com.example.mymovieapp.model.ConcreteFilm
import com.example.mymovieapp.utils.navigateToDetailsActivity
import com.example.mymovieapp.paging.FilmPagerAdapter
import com.example.mymovieapp.viewmodel.MainViewModel
import com.example.mymovieapp.viewmodel.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingFilmsFragment : Fragment(), FilmPagerAdapter.ItemClickListener {
    private lateinit var binding: FragmentUpcomingFilmsBinding
    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((requireActivity().application as FilmApplication).repository)
    }
    private val adapter = FilmPagerAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpcomingFilmsBinding.inflate(inflater, container, false)

        binding.myUpcomingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.myUpcomingRecyclerView.adapter = this.adapter

        loadStateListener(adapter, binding.myUpcomingProgressBar,binding.retryBtn)

        getData()

        binding.retryBtn.setOnClickListener {
            getData()
        }

        return binding.root
    }

    override fun onItemClick(clickedMovie: ConcreteFilm) {
        navigateToDetailsActivity(requireContext(), clickedMovie.id)
    }

    private fun getData(){
        binding.retryBtn.visibility = View.GONE
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getFilmsList("upcoming").collectLatest { data ->
                withContext(Dispatchers.Main) {
                    adapter.submitData(data)
                }
            }
        }
    }

}