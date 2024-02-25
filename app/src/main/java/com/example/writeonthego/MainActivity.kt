package com.example.writeonthego

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dbtechprojects.photonotes.ui.theme.WriteOnTheGoTheme
import com.example.writeonthego.ui.theme.CreateNote.CreateNoteScreen
import com.example.writeonthego.ui.theme.EditNote.NoteEditScreen
import com.example.writeonthego.ui.theme.NoteDetail.NoteDetailScreen
import com.example.writeonthego.ui.theme.NoteViewModelFactory
import com.example.writeonthego.ui.theme.NotesList.NoteList
import com.example.writeonthego.ui.theme.NotesList.NotesList
import com.example.writeonthego.ui.theme.NotesViewModel

class MainActivity : ComponentActivity() {

    private lateinit var notesViewModel : NotesViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WriteOnTheGoApp.getInstance()

        // retrieve viewModel
        notesViewModel =  NoteViewModelFactory(WriteOnTheGoApp.getDao()).create(NotesViewModel::class.java)


        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Constants.NAVIGATION_NOTES_LIST
            ) {
                // Notes List
                composable(Constants.NAVIGATION_NOTES_LIST) { NoteList(navController, notesViewModel) }

                // Notes Detail page
                composable(
                    Constants.NAVIGATION_NOTE_DETAIL,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTE_ID_Argument) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAVIGATION_NOTE_ID_Argument)
                        ?.let { NoteDetailScreen(noteId = it, navController, notesViewModel) }
                }

                // Notes Edit page
                composable(
                    Constants.NAVIGATION_NOTE_EDIT,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTE_ID_Argument) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAVIGATION_NOTE_ID_Argument)
                        ?.let { NoteEditScreen(noteId = it, navController, notesViewModel) }
                }

                // Create Note Page
                composable(Constants.NAVIGATION_NOTES_CREATE) { CreateNoteScreen(navController, notesViewModel) }

            }

        }
    }
}
