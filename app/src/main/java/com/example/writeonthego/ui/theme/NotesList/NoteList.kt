package com.example.writeonthego.ui.theme.NotesList

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dbtechprojects.photonotes.ui.theme.WriteOnTheGoTheme
import com.example.writeonthego.Constants
import com.example.writeonthego.Constants.orPlaceHolderList
import com.example.writeonthego.R
import com.example.writeonthego.WriteOnTheGoApp
import com.example.writeonthego.model.Note
import com.example.writeonthego.ui.theme.GenericAppBar
import com.example.writeonthego.ui.theme.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteList(noteId: Int, navController: NavController, viewModel: NotesViewModel) {
    val openDialog = remember {
        mutableStateOf(false)
    }
    val deleteText = remember {
        mutableStateOf("")
    }
    val notesQuery = remember {
        mutableStateOf("")
    }
    val notesToDelete = remember {
        mutableStateOf(listOf<Note>())
    }
    val notes = viewModel.notes.observeAsState()
    val context = LocalContext.current

    WriteOnTheGoTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            Scaffold(
                topBar = {
                    GenericAppBar(
                        title = "Edit Note",
                        onIconClick = {
                                      if(notes.value?.isNotEmpty() == true){
                                          openDialog.value = true
                                          deleteText.value = "Are you sure you want to delete all notes?"
                                          notesToDelete.value =notes.value ?: emptyList()
                                      }else{
                                          Toast.makeText(context, "No notes found", Toast.LENGTH_SHORT)
                                      }
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.note_delete),
                                contentDescription = stringResource(R.string.delete_note),
                                tint = Color.Black
                            )
                        },
                        iconState = remember {
                            mutableStateOf(true)
                        }
                    )
                },
                floatingActionButton = {
                    NotesFab(
                        contentDescription = stringResource(R.string.create_note),
                        action = {navController.navigate(Constants.NAVIGATION_NOTES_CREATE)},
                        icon =R.drawable.note_add_icon
                    )
                }
            ) {
                Column() {
                    SearchBar(notesQuery)
                    NotesList(
                        notes = notes.value.orPlaceHolderList(),
                        query = notesQuery,
                        openDialog = openDialog,
                        deleteText = deleteText,
                        navController = navController,
                        notesToDelete = notesToDelete
                    )
                }

                DeleteDialog(
                    openDialog = openDialog,
                    text = deleteText,
                    notesToDelete = notesToDelete,
                    action = {
                        notesToDelete.value.forEach {
                            viewModel.deleteNotes(it)
                        }
                    })
            }
        }
    }
}

@Composable
fun SearchBar(query: MutableState<String>) {
    Column(Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 0.dp)) {
        TextField(
            value = query.value,
            placeholder = { Text("Search..") },
            maxLines = 1,
            onValueChange = { query.value = it },
            modifier = Modifier
                .background(Color.White)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { query.value = "" }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_cross),
                            contentDescription = stringResource(
                                R.string.clear_search
                            )
                        )
                    }
                }

            })

    }
}
