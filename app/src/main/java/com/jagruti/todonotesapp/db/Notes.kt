package com.jagruti.todonotesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesData")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "title")
    var title : String = "",
    @ColumnInfo(name = "description")

    var description : String = "",
    @ColumnInfo(name = "imgPath")

    var imgPath : String = "",
    var isTaskCompleted : Boolean = false
)