package com.lguerra.ceibasoftware.domain

import com.lguerra.ceibasoftware.data.PostRepository
import com.lguerra.ceibasoftware.data.UserRepository
import com.lguerra.ceibasoftware.data.network.PostResponse
import com.lguerra.ceibasoftware.data.network.UserResponse
import com.lguerra.ceibasoftware.mappers.toPost
import com.lguerra.ceibasoftware.mappers.toPostEntity
import com.lguerra.ceibasoftware.mappers.toUser
import com.lguerra.ceibasoftware.mappers.toUserEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPostsUseCaseTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var postRepository: PostRepository
    lateinit var getPostsUseCase: GetPostsUseCase

    private val userResponse = UserResponse(1, "name1", "email1", "phone1")
    private val userEntity = userResponse.toUserEntity()!!
    private val userId = userEntity.userId
    private val postResponseList = listOf(
        PostResponse(userId, 11, "title1", "body1"),
        PostResponse(userId, 22, "title2", "body2"),
        PostResponse(userId, 33, "title3", "body3")
    )
    private val postEntityList = postResponseList.map { it.toPostEntity()!! }

    private val expected = Pair(userEntity.toUser(), postEntityList.map { it.toPost() })

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, relaxed = true)
        getPostsUseCase = GetPostsUseCase(userRepository, postRepository)
    }

    @Test
    fun `should return user with post list when get values from database`() =
        runBlocking {

            coEvery { userRepository.getUserByIdFromDatabase(userId) } returns userEntity
            coEvery { postRepository.getPostsFromDatabase(userId) } returns postEntityList
            coEvery { postRepository.getPostsFromApi(userId) } returns postResponseList

            val result = getPostsUseCase(userId)

            assert(result == expected)
            coVerify(exactly = 1) { userRepository.getUserByIdFromDatabase(userId) }
            coVerify(exactly = 1) { postRepository.getPostsFromDatabase(userId) }
            coVerify(exactly = 0) { postRepository.getPostsFromApi(userId) }
            coVerify(exactly = 0) { postRepository.insertPosts(postEntityList) }

        }

    @Test
    fun `should return user with post list and save in database when values from database are empty`() =
        runBlocking {

            coEvery { userRepository.getUserByIdFromDatabase(userId) } returns userEntity
            coEvery { postRepository.getPostsFromDatabase(userId) } returns emptyList()
            coEvery { postRepository.getPostsFromApi(userId) } returns postResponseList

            val result = getPostsUseCase(userId)

            assert(result == expected)
            coVerify(exactly = 1) { userRepository.getUserByIdFromDatabase(userId) }
            coVerify(exactly = 1) { postRepository.getPostsFromDatabase(userId) }
            coVerify(exactly = 1) { postRepository.getPostsFromApi(userId) }
            coVerify(exactly = 1) { postRepository.insertPosts(postEntityList) }

        }

}