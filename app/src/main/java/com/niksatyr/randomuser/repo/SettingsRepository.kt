package com.niksatyr.randomuser.repo

interface SettingsRepository {
    fun isNightModeEnabled(): Boolean
    fun getFetchedUsersCount(): Int
}