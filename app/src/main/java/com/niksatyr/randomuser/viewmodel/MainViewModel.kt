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

class MainViewModel(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val usersLiveData = MutableLiveData<List<User>>()

    val state = MutableLiveData<State>(Loading)

    fun loadUsers(count: Int) {
        state.value = Loading
        val scope = CoroutineScope(Job())
        scope.launch(exceptionHandler) {
            launch(Dispatchers.IO) {
                usersLiveData.postValue(userRepository.getUsers(count))
                state.postValue(Loaded)
            }
        }
    }

    fun getFetchedUsersCount() = settingsRepository.getFetchedUsersCount()

    class Factory(
        private val userRepository: UserRepository,
        private val settingsRepository: SettingsRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(userRepository, settingsRepository) as T
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        state.postValue(Failed(throwable.message))
    }

}