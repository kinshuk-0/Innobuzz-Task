package com.example.innobuzztask.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Posts::class],
    version  = 1
)
abstract class PostsDatabase: RoomDatabase() {
    abstract val dao: PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getDatabase(context: Context): PostsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): PostsDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PostsDatabase::class.java,
                "posts"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}