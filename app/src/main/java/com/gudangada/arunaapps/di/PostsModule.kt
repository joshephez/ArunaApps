package com.gudangada.arunaapps.di

import com.gudangada.arunaapps.room.AppDatabase
import com.gudangada.arunaapps.room.DAO
import com.gudangada.arunaapps.util.Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object PostsModule {
    @Provides
    @JvmStatic
    fun providesPostApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @JvmStatic
    fun providesPostsDao(appDatabase: AppDatabase): DAO {
        return appDatabase.postsDao()
    }
}