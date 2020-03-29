package com.niksatyr.randomuser.util

sealed class State

object Loading : State()

object Loaded : State()

data class Failed(val reason: String?) : State()