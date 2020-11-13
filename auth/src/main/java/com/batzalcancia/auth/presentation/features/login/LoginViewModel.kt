package com.batzalcancia.auth.presentation.features.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batzalcancia.auth.domain.usecases.LoginUser
import com.batzalcancia.core.enums.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModel @ViewModelInject constructor(
    private val loginUser: LoginUser
) : ViewModel() {

    private val _username = MutableStateFlow<String?>(null)
    var username: String? = null
        set(value) {
            field = value
            _username.value = value
        }

    private val _password = MutableStateFlow<String?>(null)
    var password: String? = null
        set(value) {
            field = value
            _password.value = value
        }

    private val _loginState = MutableStateFlow<UiState?>(null)
    val loginState: MutableStateFlow<UiState?> = _loginState

    var isButtonEnabled = _username.combine(_password) { username, password ->
        username.isNullOrEmpty().not() && password.isNullOrEmpty().not()
    }

    fun onLoginClicked() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _loginState.value = UiState.Error(throwable)
        }) {
            _loginState.value = UiState.Loading
            loginUser(
                _username.value ?: throw error("Invalid Username"),
                _password.value ?: throw error("Invalid Password")
            )
            _loginState.value = UiState.Complete

        }
    }

}