package com.batzalcancia.auth.presentation.features.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batzalcancia.auth.domain.usecases.LoginUser
import com.batzalcancia.auth.domain.usecases.RegisterUser
import com.batzalcancia.core.enums.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModel @ViewModelInject constructor(
    private val registerUser: RegisterUser,
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

    private val _country = MutableStateFlow<String?>(null)
    var country: String? = null
        set(value) {
            field = value
            _country.value = value
        }

    private val _registerState = MutableStateFlow<UiState?>(null)
    val registerState: MutableStateFlow<UiState?> = _registerState

    var isButtonEnabled = combine(_username, _password, _country) { username, password, country ->
        username != null && username.length >= 6 &&
                password != null && password.length >= 6 &&
                country.isNullOrEmpty().not()
    }

    fun onRegisterClicked() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _registerState.value = UiState.Error(throwable)
        }) {
            _registerState.value = UiState.Loading
            registerUser(
                _username.value ?: throw error("Invalid Username"),
                _password.value ?: throw error("Invalid Password"),
                _country.value ?: throw error("Invalid Country")
            )
            loginUser(
                _username.value ?: throw error("Invalid Username"),
                _password.value ?: throw error("Invalid Password")
            )
            _registerState.value = UiState.Complete
        }
    }

}