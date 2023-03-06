package com.lguerra.ceibasoftware.ui.viewmodel

import androidx.lifecycle.*
import com.lguerra.ceibasoftware.data.model.Post
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.domain.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    val user = MutableLiveData<User>()
    val posts = MutableLiveData<List<Post>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(userId: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)

            val (userResult, postsResult) = getPostsUseCase(userId = userId)

            user.postValue(userResult)
            posts.postValue(postsResult)
            isLoading.postValue(false)

        }
    }

}