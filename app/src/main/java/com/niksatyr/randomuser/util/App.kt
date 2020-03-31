package com.niksatyr.randomuser.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.niksatyr.randomuser.repo.LocalSettingsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val settingsRepository = LocalSettingsRepository(this)
        setNightMode(settingsRepository.isNightModeEnabled())
    }

    fun setNightMode(nightModeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(when (nightModeEnabled) {
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        })
    }
}