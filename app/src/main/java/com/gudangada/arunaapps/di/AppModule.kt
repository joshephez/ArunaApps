package com.gudangada.arunaapps.di

import android.app.Application
import androidx.room.Room
import com.gudangada.arunaapps.URLManager
import com.gudangada.arunaapps.room.AppDatabase
import com.xoxoer.lifemarklibrary.BuildConfig
import com.xoxoer.lifemarklibrary.Lifemark
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesLifemark(application: Application): Lifemark {
        return Lifemark(application)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "aruna_localbase.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesRetrofitInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        return Retrofit.Builder()
            .baseUrl(URLManager.BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}