package com.jagruti.todonotesapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jagruti.todonotesapp.NotesApp

class MyWorker(val context: Context,val workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.delete(true)
        return Result.success()
    }
}