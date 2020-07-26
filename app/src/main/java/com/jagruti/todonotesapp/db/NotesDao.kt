package com.jagruti.todonotesapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NotesDao {
    @Query(value = "SELECT * from notesData")
    fun getAll() : List<Notes>
    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)
    @Update
    fun updateNotes(notes: Notes)
    @Query(value = "DELETE from notesData where isTaskCompleted=:status")
    fun delete(status: Boolean)

}