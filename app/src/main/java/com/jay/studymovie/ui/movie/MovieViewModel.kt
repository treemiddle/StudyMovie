package com.jay.studymovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jay.studymovie.domain.repository.JayMovieRepository
import com.jay.studymovie.ui.base.JayViewModel
import com.jay.studymovie.ui.base.ViewModelType
import com.jay.studymovie.ui.movie.mapper.JayMoviePresentationMapper
import com.jay.studymovie.ui.movie.model.JayMoviePresentation
import com.jay.studymovie.utils.NotNullMutableLiveData
import com.jay.studymovie.utils.ext.rx
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

interface MovieViewModelType : ViewModelType<MovieViewModelType.Input, MovieViewModelType.Output> {
    interface Input {
        fun searchClick()
        fun debounceQuery(query: String)
    }

    interface Output {
        val searchText: MutableLiveData<String>
        val movieList: LiveData<List<JayMoviePresentation>>
        val isLoading: LiveData<Boolean>
    }
}

class MovieViewModel(
    private val movieRepository: JayMovieRepository
) : JayViewModel<MovieState>(), MovieViewModelType, MovieViewModelType.Input, MovieViewModelType.Output {

    override val input: MovieViewModelType.Input
        get() = this

    override val output: MovieViewModelType.Output
        get() = this

    private val _searchClick: Subject<Unit> = PublishSubject.create()
    private val _movieClick: PublishSubject<JayMoviePresentation> = PublishSubject.create()

    private val _searchText: NotNullMutableLiveData<String> = NotNullMutableLiveData(movieRepository.latestMovieQuery)
    private val _movieList: NotNullMutableLiveData<List<JayMoviePresentation>> = NotNullMutableLiveData(emptyList())
    private val _isLoading: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)

    override val searchText: MutableLiveData<String>
        get() = _searchText

    override val movieList: LiveData<List<JayMoviePresentation>>
        get() = _movieList

    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    override fun searchClick() = _searchClick.onNext(Unit)

    override fun debounceQuery(query: String) = _searchText.postValue(query)

    init {
        val query = _searchText.rx
            .toFlowable(BackpressureStrategy.DROP)
        val button = _searchClick.throttleFirst(1, TimeUnit.SECONDS)
            .map { _searchText.value }
            .toFlowable(BackpressureStrategy.DROP)

        compositeDisposable.addAll(
            Flowable.merge(query, button)
                .filter { it.length >= 2 }
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { showLoading() }
                .switchMap(movieRepository::getMovies)
                .map {
                    it.map(JayMoviePresentationMapper::mapToPresentation).map { movie ->
                        movie.apply {
                            onClick = _movieClick
                        }
                    }
                }
                .onErrorReturn { listOf() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { hideLoading() }
                .subscribe { _movieList.value = it },

            _movieClick.map(JayMoviePresentation::link)
                .subscribe { url ->
                    runState(MovieState.ShowMovieLink(url))
                }
        )
    }

    private fun showLoading() = _isLoading.postValue(true)

    private fun hideLoading() = _isLoading.postValue(false)
}