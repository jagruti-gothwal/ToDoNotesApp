package com.jagruti.todonotesapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.jagruti.todonotesapp.db.NotesDatabase

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext);

    }
    fun getNotesDb() : NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}