package com.gudangada.arunaapps.ui

import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.com.gudangada.arunaapps.ui.PostsViewModel
import com.gudangada.arunaapps.R
import com.gudangada.arunaapps.common.createLoading
import com.gudangada.arunaapps.common.onSearchPressed
import com.gudangada.arunaapps.common.onTextChange
import com.gudangada.arunaapps.entity.Model

import com.gudangada.arunaapps.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class PostActivity : DaggerAppCompatActivity() {

    private lateinit var adapterList: AdapterList
    private lateinit var postsViewModel: PostsViewModel
    private lateinit var loadingDialog: Dialog

    @Inject
    lateinit var providerFactoryFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize post view model
        postsViewModel = ViewModelProviders
            .of(this, providerFactoryFactory)
            .get(PostsViewModel::class.java)

        setContentView(R.layout.activity_main)

        // initialize ui
        initUI()
        initPostsAdapter()

        // call get network call retrofit
        postsViewModel.retrievePosts()

        // loading observer
        postsViewModel.isLoading.observe(this, Observer { loading ->
            if (loading) loadingDialog.show()
            else loadingDialog.dismiss()
        })

        // post from network calls or room
        postsViewModel.postsSuccess.observe(this@PostActivity, Observer { posts ->
            if (posts == null) return@Observer
            else bindUI(posts)
        })
    }
    private fun initUI() {
        loadingDialog = createLoading()
        editText.onTextChange { query ->
            postsViewModel.apply {
                searchPostQuery.set(query)
                if (query.isEmpty()) retrievePosts()
            }
        }
        editText.onSearchPressed {
            postsViewModel.retrievePostsWithQuery()
        }
    }

    private fun bindUI(posts: List<Model>) {
        adapterList.setPosts(posts)
    }

    private fun initPostsAdapter() {
        adapterList = AdapterList()
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = adapterList
            layoutManager = LinearLayoutManager(this@PostActivity)
        }
    }


}