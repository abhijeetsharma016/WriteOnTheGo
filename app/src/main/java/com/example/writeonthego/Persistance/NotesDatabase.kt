package com.example.writeonthego.Persistance

import androidx.room.Database
import com.example.writeonthego.model.Note


@Database(version = 1, entities = [Note:: class])
abstract class NotesDatabase {
    abstract fun NotesDao(): NotesDao
}