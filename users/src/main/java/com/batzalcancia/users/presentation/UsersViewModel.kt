package com.batzalcancia.users.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batzalcancia.core.enums.UiState
import com.batzalcancia.users.data.entities.User
import com.batzalcancia.users.domain.usecases.GetAllUsers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModel @ViewModelInject constructor(
    private val getAllUsers: GetAllUsers
) : ViewModel() {

    val usersState = MutableStateFlow<UiState>(UiState.Loading)
    val users = MutableStateFlow<List<User>>(emptyList())

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            usersState.value = UiState.Error(throwable)
        }) {
            usersState.value = UiState.Loading
            users.value = getAllUsers()
            usersState.value = UiState.Complete
        }
    }

}