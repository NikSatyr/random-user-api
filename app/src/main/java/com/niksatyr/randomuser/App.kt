package com.niksatyr.randomuser

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.niksatyr.randomuser.di.repositoryModule
import com.niksatyr.randomuser.di.retrofitModule
import com.niksatyr.randomuser.di.viewModelModule
import com.niksatyr.randomuser.repo.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val settingsRepository = SettingsRepositoryImpl(this)
        setNightMode(settingsRepository.isNightModeEnabled())
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule, repositoryModule, retrofitModule))
        }
    }

    fun setNightMode(nightModeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(when (nightModeEnabled) {
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        })
    }
}