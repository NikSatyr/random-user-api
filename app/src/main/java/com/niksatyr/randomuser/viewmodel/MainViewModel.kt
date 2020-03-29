package com.niksatyr.randomuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.niksatyr.randomuser.dto.User
import com.niksatyr.randomuser.repo.UserRepository
import com.niksatyr.randomuser.util.Failed
import com.niksatyr.randomuser.util.Loading
import com.niksatyr.randomuser.util.State
import kotlinx.coroutines.*

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val usersLiveData = MutableLiveData<List<User>>()

    val state = MutableLiveData<State>(Loading)

    fun loadUsers(count: Int = DEFAULT_USERS_COUNT) {
        scope.launch(exceptionHandler) {
            launch(Dispatchers.IO) {
                usersLiveData.postValue(userRepository.getUsers(count))
            }
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

    private val scope = CoroutineScope(Job())

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        state.postValue(Failed(throwable.message))
    }

}