package com.lguerra.ceibasoftware.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.domain.GetUsersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @MockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var usersViewModel: UsersViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val userList = listOf(
        User(1, "Yulian", "email1", "phone1"),
        User(2, "Roxy", "email2", "phone2"),
        User(3, "JuanJo", "email3", "phone3"),
        User(4, "Astrid", "email4", "phone4")
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, true)
        usersViewModel = UsersViewModel(getUsersUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should set users and loading values when view model is created`() =
        runTest {

            coEvery { getUsersUseCase() } returns userList

            usersViewModel.onCreate()

            assert(usersViewModel.users.value == userList)
            assert(usersViewModel.isLoading.value == false)

        }

    @Test
    fun `should filter users list values when is searching by valid name`() =
        runTest {

            val name = userList.first().name
            val expected = userList.filter { it.name.contains(name, true) }
            coEvery { getUsersUseCase() } returns userList

            usersViewModel.onCreate()
            usersViewModel.searchByName(name)

            assert(usersViewModel.users.value == expected)

        }

    @Test
    fun `should filter users list values when is searching by invalid name`() =
        runTest {

            val name = "invalidName"
            val expected = emptyList<User>()
            coEvery { getUsersUseCase() } returns userList

            usersViewModel.onCreate()
            usersViewModel.searchByName(name)

            assert(usersViewModel.users.value == expected)

        }

    @Test
    fun `should not filter users list values when search value is empty`() =
        runTest {

            val name = ""
            val expected = userList
            coEvery { getUsersUseCase() } returns userList

            usersViewModel.onCreate()
            usersViewModel.searchByName(name)

            assert(usersViewModel.users.value == expected)

        }

}