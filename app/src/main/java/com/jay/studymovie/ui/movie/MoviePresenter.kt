package com.jay.studymovie.ui.movie

import android.net.Uri
import com.jay.studymovie.domain.repository.JayMovieRepository
import com.jay.studymovie.ui.movie.mapper.JayMoviePresentationMapper
import com.jay.studymovie.ui.movie.model.JayMoviePresentation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class MoviePresenter(
    private val movieRepository: JayMovieRepository,
    private val view: MovieContract.View
) : MovieContract.Presenter {
    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)
    private val _querySubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _searhClickSubject: Subject<Unit> = PublishSubject.create()

    init {
        val query = _querySubject.debounce(700, TimeUnit.MILLISECONDS)
            .toFlowable(BackpressureStrategy.DROP)
        val button = _searhClickSubject.throttleFirst(1, TimeUnit.SECONDS)
            .map { _querySubject.value }
            .toFlowable(BackpressureStrategy.DROP)

        Flowable.merge(query, button)
            .filter { it.length >= 2 }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.showLoading() }
            .switchMap( movieRepository::getMovies)
            .map { it.map(JayMoviePresentationMapper::mapToPresentation) }
            .onErrorReturn { listOf() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.hideLoading() }
            .subscribe(view::showMovieList)
            .addTo(compositeDisposable)
    }

    override fun create() {
        view.setLatestQuery(query = movieRepository.latestMovieQuery)
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    override fun debounceQuery(query: String) = _querySubject.onNext(query)

    override fun buttonClickQuery() = _searhClickSubject.onNext(Unit)

    override fun searchResultClick(item: JayMoviePresentation) {
        val linkString = item.link.takeIf(String::isNotBlank) ?: return
        val uri = kotlin.runCatching { Uri.parse(linkString) }.getOrNull() ?: return
        view.movieItemClick(uri)
    }
}