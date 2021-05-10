package com.jay.studymovie.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jay.studymovie.R
import com.jay.studymovie.databinding.ActivityMovieBinding
import com.jay.studymovie.ui.base.BaseActivity

class MovieActivity : BaseActivity<ActivityMovieBinding, MovieViewModel, MovieState>(
    R.layout.activity_movie
) {
    override val viewModel: MovieViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(
                    movieRepository = requireApplication().movieRepository
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
    }

    override fun onState(newState: MovieState) {
        when (newState) {
            is MovieState.ShowMovieLink -> {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = newState.movieLink.toUri()
                })
            }
        }
    }

    private fun initAdapter() {
        binding.rvSearchResult.adapter = MovieAdapter()
    }

}