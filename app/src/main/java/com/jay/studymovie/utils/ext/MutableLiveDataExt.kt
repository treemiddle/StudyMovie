package com.jay.studymovie.utils.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.Observable
val <E> MutableLiveData<E>.rx: Observable<E>
    get() {
        return Observable.create { emitter ->
            if (!emitter.isDisposed && value != null) {
                emitter.onNext(value!!)
            }

            val observer: Observer<E> = Observer {
                if (!emitter.isDisposed && value != null) {
                    emitter.onNext(value!!)
                }
            }

            this.observeForever(observer)

            emitter.setCancellable {
                this.removeObserver(observer)
            }
        }
    }