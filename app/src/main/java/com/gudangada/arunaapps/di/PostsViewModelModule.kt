package com.gudangada.arunaapps.di

import androidx.lifecycle.ViewModel
import com.com.gudangada.arunaapps.ui.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PostsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(postsViewModel: PostsViewModel): ViewModel
}