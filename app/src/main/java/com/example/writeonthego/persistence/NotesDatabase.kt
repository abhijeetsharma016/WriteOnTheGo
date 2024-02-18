package com.example.writeonthego.persistence

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import com.example.writeonthego.model.Note

@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase {
abstract fun NotesDao(): NotesDao

}