package com.example.writeonthego.ui.theme.CreateNote


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
fun CreateNoteScreen(navController: NavController, viewModel: NotesViewModel) {
    val currentNote = remember {
        mutableStateOf("")
    }
    val currentTitle = remember {
        mutableStateOf("")
    }
    val currentPhotos = remember {
        mutableStateOf("")
    }
    val saveButtonState = remember {
        mutableStateOf(false)
    }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            WriteOnTheGoApp.getUriPermission(uri)
        }

        currentPhotos.value = uri.toString()
    }

    WriteOnTheGoTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            Scaffold(
                topBar = {
                    GenericAppBar(
                        title = "Create Note",
                        onIconClick = {
                            viewModel.createNote(
                                currentTitle.value,
                                currentNote.value,
                                currentPhotos.value
                            )
                            navController.popBackStack()
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.save),
                                contentDescription = stringResource(R.string.save_note),
                                tint = Color.Black
                            )
                        },
                        iconState = saveButtonState
                    )
                },
                floatingActionButton = {
                    NotesFab(
                        contentDescriptipon = stringResource(R.string.add_photo),
                        action ={
                            getImageRequest.launch(arrayOf("image/*"))
                        },
                        icon = R.drawable.camera
                    )
                },
                content = {
                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {
                        if (currentPhotos.value!!.isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(data = Uri.parse(currentPhotos.value))
                                        .build()
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.3f)
                                    .padding(6.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        TextField(
                            value = currentTitle.value,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange ={value->
                                currentTitle.value= value
                                saveButtonState.value = currentTitle.value!= "" && currentNote.value != ""
                            },
                            label = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentTitle.value,
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth(),

                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black
                            ),
                            onValueChange = { value ->
                                currentNote.value = value
                                saveButtonState.value = currentTitle.value!= "" && currentNote.value != ""
                            },
                            label = { Text(text = "Body")}
                        )
                    }
                }
            )
        }
    }
}