package com.example.writeonthego.persistance

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.writeonthego.model.Note

@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [
    Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao

}