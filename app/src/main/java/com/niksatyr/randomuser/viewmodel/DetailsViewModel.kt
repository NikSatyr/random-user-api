package com.niksatyr.randomuser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niksatyr.randomuser.model.User

class DetailsViewModel : ViewModel() {

    val user = MutableLiveData<User>()

    fun setUser(user: User) {
        this.user.value = user
    }

}