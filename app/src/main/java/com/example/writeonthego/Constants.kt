package com.example.writeonthego

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.writeonthego.model.Note

object Constants {
    const val NAVIGATION_NOTES_LIST = "notesList"
    const val NAVIGATION_NOTES_CREATE = "notesCreated"
    const val NAVIGATION_NOTE_DETAIL = "noteDetail/{noteId}"
    const val NAVIGATION_NOTE_EDIT= "noteEdit/{noteId}"
    const val NAVIGATION_NOTE_ID_Argument = "noteId"
    const val TABLE_NAME = "Notes"
    const val DATABASE_NAME = "NotesDatabase"


    fun noteEditNavigation(noteId: Int) = "noteEdit/$noteId"
    fun noteDetailNavigation(noteId : Int) = "noteDetail/$noteId"
    @RequiresApi(Build.VERSION_CODES.O)
    fun List<Note>?.orPlaceHolderList(): List<Note> {
        fun placeHolderList(): List<Note> {
            return listOf(Note(id = 0, title = "No Notes Found", note = "Please create a note.", dateUpdated = ""))
        }
        return if (this != null && this.isNotEmpty()){
            this
        } else placeHolderList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val noteDetailPlaceHolder = Note(note = "Cannot Find note detail", id = 0, title = "Cannot find note detail")
}