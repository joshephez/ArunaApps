package com.gudangada.arunaapps.di

import androidx.lifecycle.ViewModelProvider
import com.gudangada.arunaapps.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(
        viewModelProviderFactoryFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}