package com.jay.studymovie.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jay.studymovie.R
import com.jay.studymovie.domain.repository.JayAuthRepository
import com.jay.studymovie.ui.base.JayViewModel
import com.jay.studymovie.ui.base.ViewModelType
import com.jay.studymovie.utils.NotNullMutableLiveData
import com.jay.studymovie.utils.ResourceProvider
import com.jay.studymovie.utils.SingleEvent
import com.jay.studymovie.utils.ext.liveOf
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

interface LoginViewModelType : ViewModelType<LoginViewModelType.Input, LoginViewModelType.Output> {

    interface Input {
        fun loginClick()
    }

    interface Output {
        val errorId: LiveData<String>
        val errorPassword: LiveData<String>
        val id: MutableLiveData<String>
        val password: MutableLiveData<String>
        val isLoading: LiveData<Boolean>
        val emptyMessage: LiveData<String>
    }
}

class LoginViewModel(
    private val resourceProvider: ResourceProvider
) : JayViewModel<LoginState>(), LoginViewModelType, LoginViewModelType.Input,
    LoginViewModelType.Output {
    override val input: LoginViewModelType.Input
        get() = this

    override val output: LoginViewModelType.Output
        get() = this

    private val _loginAction: Subject<Unit> = PublishSubject.create()
    private val _errorId: MutableLiveData<String> = MutableLiveData()
    private val _errorPassword: MutableLiveData<String> = MutableLiveData()
    private val _emptyMessage: MutableLiveData<String> = MutableLiveData()

    private val _id: NotNullMutableLiveData<String> = NotNullMutableLiveData("")
    private val _password: NotNullMutableLiveData<String> = NotNullMutableLiveData("")
    private val _isLoading: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)

    override val errorId: LiveData<String>
        get() = _errorId

    override val errorPassword: LiveData<String>
        get() = _errorPassword

    override val id: MutableLiveData<String>
        get() = _id

    override val password: MutableLiveData<String>
        get() = _password

    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    override val emptyMessage: LiveData<String>
        get() = _emptyMessage

    override fun loginClick() = _loginAction.onNext(Unit)

    init {
        compositeDisposable.addAll(
            _loginAction
                .throttleFirst(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map { id to password }
                .subscribe { (id, password) ->
                    showLoading()
                    clearNulls()

                    when {
                        id.value!!.isEmpty() || password.value!!.isEmpty() -> {
                            hideLoading()
                            emptyMessage(resourceProvider.getString(R.string.login_empty))
                        }
                        id.value != LoginActivity.TEST_ID -> {
                            hideLoading()
                            showIdError()
                        }
                        password.value != LoginActivity.TEST_PASSWORD -> {
                            hideLoading()
                            showPasswordError()
                        }
                        else -> {
                            runState(LoginState.ShowMain)
                        }
                    }
                }
        )
    }

    private fun clearNulls() {
        _errorId.value = null
        _errorPassword.value = null
    }

    private fun showLoading() = _isLoading.postValue(true)

    private fun hideLoading() = _isLoading.postValue(false)

    private fun showIdError() = _errorId.postValue(resourceProvider.getString(R.string.login_fail_id))

    private fun showPasswordError() = _errorPassword.postValue(resourceProvider.getString(R.string.login_fail_password))

    private fun emptyMessage(message: String) = _emptyMessage.postValue(message)

}