package com.lguerra.ceibasoftware.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lguerra.ceibasoftware.data.model.Post
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.domain.GetPostsUseCase
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
class PostsViewModelTest {

    @MockK
    private lateinit var getPostsUseCase: GetPostsUseCase

    private lateinit var postsViewModel: PostsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val user = User(1, "name", "email", "phone")
    private val userId = user.id
    private val postList = listOf(
        Post("title1", "body1"),
        Post( "title2", " body2"),
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, true)
        postsViewModel = PostsViewModel(getPostsUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should set users posts and loading values when view model is created`() =
        runTest {

            coEvery { getPostsUseCase(userId) } returns Pair(user, postList)

            postsViewModel.onCreate(userId)

            assert(postsViewModel.user.value == user)
            assert(postsViewModel.posts.value == postList)
            assert(postsViewModel.isLoading.value == false)

        }

}