package com.gudangada.arunaapps.di

import com.gudangada.arunaapps.ui.PostActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            PostsViewModelModule::class,
            PostsModule::class
        ]
    )
    abstract fun contributePostActivity(): PostActivity

}