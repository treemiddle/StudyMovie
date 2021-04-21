package com.jay.studymovie.ui.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.jay.studymovie.R
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.movie.mapper.JayMoviePresentationMapper
import com.jay.studymovie.ui.movie.model.JayMoviePresentation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class MovieActivity : BaseActivity<MoviePresenter>(), MovieContract.View {
    private lateinit var etQuery: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressbar: ProgressBar

    private lateinit var adapter: MovieAdapter

    override val presenter: MoviePresenter by lazy {
        MoviePresenter(
            movieRepository = requireApplication().movieRepository,
            view = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        queryTexListener()
        onSearchClick()
    }

    private fun initView() {
        etQuery = findViewById(R.id.et_query)
        btnSearch = findViewById(R.id.btn_search)
        recyclerView = findViewById(R.id.rv_search_result)
        progressbar = findViewById(R.id.pb_loading)
    }

    private fun initRecyclerView() {
        if (!::adapter.isInitialized) {
            adapter = MovieAdapter(
                presenter::searchResultClick
            )
        }

        recyclerView.adapter = adapter
    }

    override fun setLatestQuery(query: String) {
        etQuery.setText(query)
    }

    override fun showMovieList(items: List<JayMoviePresentation>) {
        adapter.submitList(items)
    }

    override fun movieItemClick(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
    }

    private fun queryTexListener() = etQuery.addTextChangedListener {
        presenter.debounceQuery(it.toString())
    }

    private fun onSearchClick() = btnSearch.setOnClickListener {
        presenter.buttonClickQuery()
    }

}