package com.niksatyr.randomuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.niksatyr.randomuser.model.User
import com.niksatyr.randomuser.repo.SettingsRepository
import com.niksatyr.randomuser.repo.UserRepository
import com.niksatyr.randomuser.util.Failed
import com.niksatyr.randomuser.util.Loaded
import com.niksatyr.randomuser.util.Loading
import com.niksatyr.randomuser.util.State
import kotlinx.coroutines.*
import java.util.*

class MainViewModel(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        state.postValue(Failed(throwable.message))
    }

    val usersLiveData = MutableLiveData<List<User>>()

    val state = MutableLiveData<State>()

    fun loadUsers(count: Int) {
        state.value = Loading
        val scope = CoroutineScope(Job())
        scope.launch(exceptionHandler) {
            launch(Dispatchers.IO) { // Inner scope needed to handle exceptions
                usersLiveData.postValue(userRepository.getUsers(count))
                state.postValue(Loaded)
            }
        }
    }

    fun getFetchedUsersCount() = settingsRepository.getFetchedUsersCount()

    fun clearLoadedUsers() {
        usersLiveData.value = Collections.emptyList()
    }

    fun isLoadingInProgress() = state.value == Loading

    class Factory(
        private val userRepository: UserRepository,
        private val settingsRepository: SettingsRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(userRepository, settingsRepository) as T
        }
    }

}