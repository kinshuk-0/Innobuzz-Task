package com.example.innobuzztask.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {

    @Upsert
    fun updatePost(post: Posts)

    @Query("SELECT * FROM posts")
    fun getPosts(): List<Posts>
}