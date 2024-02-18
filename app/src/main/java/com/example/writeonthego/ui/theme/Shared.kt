package com.example.writeonthego.ui.theme

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GenericAppBar(
    title: String,
    onIconClick: (() -> Unit)?,
    icon: @Composable() (() -> Unit)?,
    iconState: MutableState<Boolean>
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(
                onClick = {
                    onIconClick?.invoke()
                },
                content = {
                    if (iconState.value){
                        icon?.invoke()
                    }
                }

            )
        }
    )
}
@Composable
@Preview
fun GenericAppBarPreview() {
    // Example usage of GenericAppBar in the preview
    val iconState = remember { mutableStateOf(true) }

    GenericAppBar(
        title = "Sample App Bar",
        onIconClick = {
            // Handle icon click action
        },
        icon = {
            // Composable icon content
            Icon(Icons.Default.Home, contentDescription = "Home Icon")
        },
        iconState = iconState
    )
}
