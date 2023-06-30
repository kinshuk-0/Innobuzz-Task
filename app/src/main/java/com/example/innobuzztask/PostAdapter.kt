package com.example.innobuzztask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.innobuzztask.model.Posts

class PostAdapter(private val list: ArrayList<Posts>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    inner class PostViewHolder(view: View, listener: PostClickListener): RecyclerView.ViewHolder(view) {
        val title: TextView

        init {
            title  = view.findViewById(R.id.postTitle)
            view.setOnClickListener {
                listener.onPostClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_display, parent, false)
        return PostViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.title.text = post.title
    }

    private lateinit var mListener: PostClickListener

    interface PostClickListener {
        fun onPostClicked(position: Int)
    }

    fun setOnPostClickListener(listener: PostClickListener) {
        mListener = listener
    }
}