package com.gudangada.arunaapps.ui

import com.gudangada.arunaapps.entity.Model
import com.gudangada.arunaapps.room.DAO
import com.gudangada.arunaapps.util.Api
import com.gudangada.arunaapps.util.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postsApi: Api,
    private val postsDao: DAO
) {
    private fun persistPosts(posts: List<Model>) {
        posts.forEach { post -> postsDao.insertPost(post) }
    }

    fun retrievePostsWithQuery(
        query: String,
        onStart: () -> Unit,
        onFinish: () -> Unit,
        handler: SingleObserver<List<Model>>
    ) {
        postsDao.getPostWithQuery(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe(handler)
    }

    fun retrievePosts(
        onStart: () -> Unit,
        onFinish: () -> Unit,
        handler: SingleObserver<List<Model>>
    ) {
        return postsApi.retrievePosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .doOnSuccess { posts -> persistPosts(posts) }
            .doOnError {
                postsDao.getPosts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { onStart() }
                    .doOnTerminate { onFinish() }
                    .subscribe(handler)
            }
            .subscribe(handler)
    }
}