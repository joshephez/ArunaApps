package com.gudangada.arunaapps.util

import com.gudangada.arunaapps.entity.Model
import io.reactivex.Single
import retrofit2.http.GET

interface Api {
    @GET("/posts")
    fun retrievePosts(): Single<List<Model>>
}