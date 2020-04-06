package com.niksatyr.randomuser.di

import com.niksatyr.randomuser.repo.LocalSettingsRepository
import com.niksatyr.randomuser.repo.RemoteUserRepository
import com.niksatyr.randomuser.repo.SettingsRepository
import com.niksatyr.randomuser.repo.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SettingsRepository> { LocalSettingsRepository(get()) }
    single<UserRepository> { RemoteUserRepository(get()) }
}
