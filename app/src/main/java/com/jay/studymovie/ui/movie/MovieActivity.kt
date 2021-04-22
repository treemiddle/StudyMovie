package com.jay.studymovie.ui.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.jay.studymovie.R
import com.jay.studymovie.databinding.ActivityMovieBinding
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.movie.model.JayMoviePresentation

class MovieActivity : BaseActivity<ActivityMovieBinding, MoviePresenter>(R.layout.activity_movie), MovieContract.View {

    private lateinit var adapter: MovieAdapter

    override val presenter: MoviePresenter by lazy {
        MoviePresenter(
            movieRepository = requireApplication().movieRepository,
            view = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        queryTexListener()
        onSearchClick()
    }

    private fun initRecyclerView() {
        if (!::adapter.isInitialized) {
            adapter = MovieAdapter(
                presenter::searchResultClick
            )
        }

        binding.rvSearchResult.adapter = adapter
    }

    override fun setLatestQuery(query: String) {
        binding.etQuery.setText(query)
    }

    override fun showMovieList(items: List<JayMoviePresentation>) {
        adapter.submitList(items)
    }

    override fun movieItemClick(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
    }

    private fun queryTexListener() = binding.etQuery.addTextChangedListener {
        presenter.debounceQuery(it.toString())
    }

    private fun onSearchClick() = binding.btnSearch.setOnClickListener {
        presenter.buttonClickQuery()
    }

}