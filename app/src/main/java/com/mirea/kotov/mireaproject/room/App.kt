package com.mirea.kotov.mireaproject.room

import android.app.Application
import androidx.room.Room

class App: Application() {
    companion object{
        var instance: App? = null

        @JvmName("getInstance1")
        fun getInstance(): App {
            return instance!!
        }

    }
    private var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    fun getDatabase(): AppDatabase {
        return database!!
    }
}