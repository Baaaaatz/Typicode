package com.batzalcancia.users

import com.batzalcancia.core.enums.UiState
import com.batzalcancia.testutils.BaseTest
import com.batzalcancia.testutils.users
import com.batzalcancia.users.domain.usecases.GetAllUsers
import com.batzalcancia.users.presentation.UsersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest : BaseTest() {

    private lateinit var viewModel: UsersViewModel
    private lateinit var getAllUsers: GetAllUsers

    @BeforeTest
    fun setup() {
        getAllUsers = mockk()
        viewModel = UsersViewModel(getAllUsers)
    }

    @Test
    fun `getUsersState should be complete when app loads users`() = runBlocking {
        // Given
        coEvery {
            getAllUsers()
        } returns users

        // When
        viewModel = UsersViewModel(getAllUsers)

        // Then
        assertEquals(UiState.Complete, viewModel.usersState.value)
    }


    @Test
    fun `getUsersState should be error request fails`() = runBlocking {
        // Given
        coEvery {
            getAllUsers()
        } throws Exception()

        // When
        viewModel = UsersViewModel(getAllUsers)

        // Then
        assertTrue(viewModel.usersState.value is UiState.Error)
    }

    @Test
    fun `users should return 10 items when request is successful`() = runBlocking {
        // Given
        coEvery {
            getAllUsers()
        } returns users

        // When
        viewModel = UsersViewModel(getAllUsers)

        // Then
        assertEquals(viewModel.users.value.size, 10)
    }

}
