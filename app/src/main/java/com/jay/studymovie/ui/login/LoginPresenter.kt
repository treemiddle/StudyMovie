package com.jay.studymovie.ui.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LoginPresenter(
    private val view: LoginContract.View
) : LoginContract.Presenter {
    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)
    private val loginAction: PublishSubject<Pair<String, String>> = PublishSubject.create()

    init {
        loginAction
            .throttleFirst(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe { (id, password) ->
                with(view) {
                    showLoading()

                    when {
                        id.isEmpty() || password.isEmpty() -> {
                            hideLoading()
                            emptyError()
                        }
                        id != LoginActivity.TEST_ID -> {
                            hideLoading()
                            showIdError()
                        }
                        password != LoginActivity.TEST_PASSWORD -> {
                            hideLoading()
                            showPasswordError()
                        }
                        else -> {
                            loginSuccess()
                        }
                    }
                }
            }
            .addTo(compositeDisposable)
    }

    override fun login(id: String, password: String) {
        loginAction.onNext(id to password)
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

}
