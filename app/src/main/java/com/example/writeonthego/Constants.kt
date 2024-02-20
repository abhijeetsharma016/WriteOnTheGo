package com.example.writeonthego

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.writeonthego.model.Note

object Constants {
    const val TABLE_NAME = "Notes"
    const val DATABASE_NAME = "NotesDatabase"

    fun noteEditNavigation(noteId: Int) = "noteEdit/$noteId"

    @RequiresApi(Build.VERSION_CODES.O)
    val noteDetailPlaceHolder = Note(note = "Cannot Find note detail", id = 0, title = "Cannot find note detail")
}