package com.niksatyr.randomuser

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.niksatyr.randomuser.di.RepositoryModule
import com.niksatyr.randomuser.di.ViewModelModule
import com.niksatyr.randomuser.repo.LocalSettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val settingsRepository = LocalSettingsRepository(this)
        setNightMode(settingsRepository.isNightModeEnabled())
        startKoin {
            androidContext(this@App)
            modules(listOf(RepositoryModule.repositoryModule, ViewModelModule.viewModelModule))
        }
    }

    fun setNightMode(nightModeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(when (nightModeEnabled) {
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        })
    }
}