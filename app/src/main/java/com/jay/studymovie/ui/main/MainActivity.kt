package com.jay.studymovie.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.jay.studymovie.R
import com.jay.studymovie.network.NetworkService
import com.jay.studymovie.network.model.MovieModel
import com.jay.studymovie.network.model.response.NaverSearchResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)
    private val _querySubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _searhClickSubject: Subject<Unit> = PublishSubject.create()

    private lateinit var etQuery: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressbar: ProgressBar

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        queryTexListener()
        onSearchClick()
        bindRx()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun initView() {
        etQuery = findViewById(R.id.et_query)
        btnSearch = findViewById(R.id.btn_search)
        recyclerView = findViewById(R.id.rv_search_result)
        progressbar = findViewById(R.id.pb_loading)
    }

    private fun initRecyclerView() {
        if (!::adapter.isInitialized) {
            adapter = MainAdapter(this::onMovieClick)
        }

        recyclerView.adapter = adapter
    }

    private fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressbar.visibility = View.INVISIBLE
    }

    private fun onMovieClick(item: MovieModel) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))

    private fun queryTexListener() = etQuery.addTextChangedListener { _querySubject.onNext(it.toString()) }

    private fun onSearchClick() = btnSearch.setOnClickListener { _searhClickSubject.onNext(Unit) }

    private fun bindRx() {
        val query = _querySubject.debounce(700, TimeUnit.MILLISECONDS)
        val button = _searhClickSubject.throttleFirst(1, TimeUnit.SECONDS)
            .map { _querySubject.value }

        Observable.merge(query, button)
            .filter { it.length >= 2 }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { showLoading() }
            .switchMapSingle(NetworkService.movieApi::getSearchMovie)
            .map(NaverSearchResponse<MovieModel>::items)
            .onErrorReturn { listOf() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { hideLoading() }
            .subscribe(adapter::submitList)
            .addTo(compositeDisposable)
    }

}