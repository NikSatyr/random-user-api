package com.niksatyr.randomuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.niksatyr.randomuser.dto.User
import com.niksatyr.randomuser.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val usersLiveData = MutableLiveData<List<User>>()

    fun loadUsers(count: Int = DEFAULT_USERS_COUNT) {
        CoroutineScope(Dispatchers.IO).launch {
            usersLiveData.postValue(userRepository.getUsers(count))
        }
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(userRepository) as T
        }
    }

    companion object {
        private const val DEFAULT_USERS_COUNT = 20
    }

}