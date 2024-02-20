package com.example.writeonthego.ui.theme.NoteDetail

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.writeonthego.Constants
import com.example.writeonthego.Constants.noteDetailPlaceHolder
import com.example.writeonthego.R
import com.example.writeonthego.ui.theme.GenericAppBar
import com.example.writeonthego.ui.theme.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteDetailScreen(noteId: Int, navController: NavController, viewModel: NotesViewModel){
    val scope = rememberCoroutineScope()
    val note =remember{
        mutableStateOf(noteDetailPlaceHolder)
    }

    LaunchedEffect(true){
        scope.launch(Dispatchers.IO){
            note.value =viewModel.getNote(noteId) ?: noteDetailPlaceHolder
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Scaffold(
            topBar ={
                GenericAppBar(
                    title = note.value.title,
                    onIconClick = {
                                  navController.navigate(Constants.noteEditNavigation(note.value.id ?: 0))
                    },
                    icon = {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.edit_note),
                            contentDescription = stringResource(R.string.edit_note),
                            tint = Color.Black,
                        )

                    },
                    iconState = remember {
                        mutableStateOf(true)
                    }
                )
            },
        ) {
            Column(Modifier.fillMaxSize()) {
                if(note.value.imageUri != null && note.value.imageUri!!.isNotEmpty()){
                    Image(painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = Uri.parse(note.value.imageUri))
                    ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .fillMaxWidth()
                            .padding(6.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = note.value.title,
                    modifier = Modifier.padding(top = 24.dp, start = 12.dp, end = 24.dp),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = note.value.dateUpdated, Modifier.padding(12.dp), color = Color.Gray)
                Text(text = note.value.note, Modifier.padding(12.dp))
            }

        }
    }
}