package com.example.writeonthego.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.writeonthego.model.Note
import com.example.writeonthego.persistance.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(
    private val db: NotesDao,
) : ViewModel() {
    val notes: LiveData<List<Note>> = db.getNotes()

    fun deleteNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            db.updateNote(note)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNote(title: String, note: String, image: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            db.insertNote(Note(title = title, note = note, imageUri = image))
        }
    }

    fun getNote(noteId: Int): Note?{
        return db.getNoteById(noteId)
    }
}

class NoteViewModelFactory(
private val db:NotesDao,
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(
            db = db,
        )as T
    }
}