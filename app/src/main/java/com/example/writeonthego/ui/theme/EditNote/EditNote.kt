package com.example.writeonthego.ui.theme.EditNote

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.writeonthego.Constants
import com.example.writeonthego.WriteOnTheGoApp
import com.example.writeonthego.ui.theme.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteEditScreen(noteId: Int, navController: NavController, viewModel: NotesViewModel){
    val scope = rememberCoroutineScope()
    val note = remember {
        mutableStateOf(Constants.noteDetailPlaceHolder)
    }
    val currentNote = remember {
        mutableStateOf(note.value.note)
    }
    val currentTitle = remember {
        mutableStateOf((note.value.title))
    }
    val currentPhotos = remember{
        mutableStateOf(note.value.imageUri)
    }
    val saveButtonState = remember{
        mutableStateOf(false)
    }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ){uri ->
        if(uri != null){
            WriteOnTheGoApp.getUriPermission(uri)
        }

        currentPhotos.value = uri.toString()

        if(currentPhotos.value != note.value.imageUri){
            saveButtonState.value = true
        }
    }
    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            note.value = viewModel.getNote(noteId)?:Constants.noteDetailPlaceHolder
            currentNote.value = note.value.note
            currentTitle.value = note.value.title
            currentPhotos.value = note.value.imageUri
        }
    }


}