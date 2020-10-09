package com.gudangada.arunaapps.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gudangada.arunaapps.R
import com.gudangada.arunaapps.entity.Model
import kotlinx.android.synthetic.main.list_view_recycler.view.*

class AdapterList : RecyclerView.Adapter<AdapterList.AdapterViewHolder>() {

    private val posts: MutableList<Model> = mutableListOf()

    internal fun setPosts(posts: List<Model>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    inner class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.textView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_recycler, parent, false)
        return AdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val post = posts[position]
        with(holder) {
            tvTitle.text = post.title

            Log.e("test hasil", post.title)
            Log.e("test hasil", posts.size.toString())



        }
    }






}