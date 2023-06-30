package com.example.innobuzztask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Posts(
    val body: String,
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
)