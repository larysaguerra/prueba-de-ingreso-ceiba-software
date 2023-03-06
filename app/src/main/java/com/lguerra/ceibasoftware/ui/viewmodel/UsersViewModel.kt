package com.lguerra.ceibasoftware.ui.viewmodel

import androidx.lifecycle.*
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.domain.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private var _users = MutableLiveData<List<User>>()

    val users: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)

            val result = getUsersUseCase()

            _users.postValue(result)

            users.postValue(result)
            isLoading.postValue(false)

        }
    }

    fun searchByName(searchText: String) {
        if (searchText.isNotEmpty()) {
            val filteredList = _users.value?.filter { it.name.contains(searchText, true) }
            users.value = filteredList ?: emptyList()
        } else {
            users.value = _users.value
        }
    }

}