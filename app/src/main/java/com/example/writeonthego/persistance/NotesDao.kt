package com.example.writeonthego.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.writeonthego.model.Note

@Dao
interface NotesDao {


    //TODO getnotesByID


    @Query("SELECT * FROM Notes ORDER BY dateUpdated DESC")
    fun getNotes(): LiveData<List<Note>>

    @Delete
    fun deleteNote(note: Note): Int

    @Update
    fun updateNote(note: Note): Int

    @Insert
    fun insertNote(note: Note)

}