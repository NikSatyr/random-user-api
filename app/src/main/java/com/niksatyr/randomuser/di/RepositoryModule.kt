package com.niksatyr.randomuser.di

import com.niksatyr.randomuser.repo.SettingsRepositoryImpl
import com.niksatyr.randomuser.repo.UserRepositoryImpl
import com.niksatyr.randomuser.repo.SettingsRepository
import com.niksatyr.randomuser.repo.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}
