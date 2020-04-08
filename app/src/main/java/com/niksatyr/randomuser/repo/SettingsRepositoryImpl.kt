package com.niksatyr.randomuser.repo

import android.content.Context
import androidx.preference.PreferenceManager

class SettingsRepositoryImpl(context: Context) : SettingsRepository {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun isNightModeEnabled(): Boolean {
        return preferences.getBoolean(PREFERENCE_NIGHT_MODE_KEY, DEFAULT_NIGHT_MODE)
    }

    override fun getFetchedUsersCount(): Int {
        return preferences.getString(PREFERENCE_FETCHED_USERS_COUNT_KEY, DEFAULT_USERS_COUNT)?.toInt()
            ?: throw IllegalStateException("Failed to load users count from preferences")
    }

    companion object {
        private const val PREFERENCE_NIGHT_MODE_KEY = "pref_night_mode"
        private const val PREFERENCE_FETCHED_USERS_COUNT_KEY = "pref_users_count"

        private const val DEFAULT_NIGHT_MODE = false
        private const val DEFAULT_USERS_COUNT = "20"
    }

}