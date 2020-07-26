package com.jagruti.todonotesapp.clicklistner

import com.jagruti.todonotesapp.db.Notes


interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}