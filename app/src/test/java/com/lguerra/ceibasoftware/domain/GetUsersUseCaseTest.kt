package com.lguerra.ceibasoftware.domain

import com.lguerra.ceibasoftware.data.UserRepository
import com.lguerra.ceibasoftware.data.network.UserResponse
import com.lguerra.ceibasoftware.mappers.toUser
import com.lguerra.ceibasoftware.mappers.toUserEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    @MockK
    private lateinit var repository: UserRepository
    lateinit var getUsersUseCase: GetUsersUseCase

    private val userResponseList = listOf(
        UserResponse(1, "name1", "email1", "phone1"),
        UserResponse(2, "name2", "email2", "phone2"),
        UserResponse(3, "name3", "email3", "phone4")
    )
    private val userEntityList = userResponseList.map { it.toUserEntity()!! }
    private val expected = userEntityList.map { it.toUser() }

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `should return user list when get values from database`() =
        runBlocking {

            coEvery { repository.getUsersFromDatabase() } returns userEntityList
            coEvery { repository.getUsersFromApi() } returns userResponseList

            val result = getUsersUseCase()

            assert(result == expected)
            coVerify(exactly = 1) { repository.getUsersFromDatabase() }
            coVerify(exactly = 0) { repository.getUsersFromApi() }
            coVerify(exactly = 0) { repository.insertUsers(userEntityList) }

        }

    @Test
    fun `should return user list and save in database when values from database are empty`() =
        runBlocking {

            coEvery { repository.getUsersFromDatabase() } returns emptyList()
            coEvery { repository.getUsersFromApi() } returns userResponseList

            val underTest = getUsersUseCase()

            //Then
            assert(underTest == expected)
            coVerify(exactly = 1) { repository.getUsersFromDatabase() }
            coVerify(exactly = 1) { repository.getUsersFromApi() }
            coVerify(exactly = 1) { repository.insertUsers(userEntityList) }

        }

}