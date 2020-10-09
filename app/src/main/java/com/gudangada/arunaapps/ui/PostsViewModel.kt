package com.com.gudangada.arunaapps.ui

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gudangada.arunaapps.common.toWhereLikeFormat
import com.gudangada.arunaapps.entity.Model
import com.gudangada.arunaapps.ui.PostRepository
import com.gudangada.arunaapps.util.RxSingleHandler
import com.gudangada.arunaapps.viewmodels.ViewModelContract
import com.xoxoer.lifemarklibrary.Lifemark
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    application: Application,
    lifemark: Lifemark,
    private val postRepository: PostRepository
) : ViewModel(), ViewModelContract {

    val searchPostQuery = ObservableField<String>()

    private val _postsSuccess = MutableLiveData<List<Model>>()
    val postsSuccess: LiveData<List<Model>>
        get() = _postsSuccess

    private val rxSingleHandler = RxSingleHandler(application, lifemark, this)
    override val isLoading = MutableLiveData<Boolean>()
    override val error = ObservableField<Boolean>()
    override val errorReason = ObservableField<String>()

    private fun onStart() {
        isLoading.value = true
    }

    private fun onFinish() {
        isLoading.value = false
    }

    fun retrievePosts() {
        postRepository.retrievePosts(
            { onStart() },
            { onFinish() },
            rxSingleHandler.handler(_postsSuccess)
        )
    }

    fun retrievePostsWithQuery(){
        postRepository.retrievePostsWithQuery(
            searchPostQuery.get()!!.toWhereLikeFormat(),
            { onStart() },
            { onFinish() },
            rxSingleHandler.handler(_postsSuccess)
        )
    }

}