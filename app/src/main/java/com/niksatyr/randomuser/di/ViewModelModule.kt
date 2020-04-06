package com.niksatyr.randomuser.di

import com.niksatyr.randomuser.viewmodel.DetailsViewModel
import com.niksatyr.randomuser.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModelModule {

    companion object {

        val viewModelModule = module {
            viewModel { MainViewModel(get(), get()) }
            viewModel { DetailsViewModel() }
        }

    }

}