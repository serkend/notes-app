package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(navController: NavController) {
    val viewModel: AddEditNoteViewModel = hiltViewModel()
    val eventFlow = viewModel.eventFlow
    val noteTitle = viewModel.noteTitle.collectAsState().value
    val noteContent = viewModel.noteContent.collectAsState().value

    val colorState = viewModel.noteColor.collectAsState()
    val colorAnimation = if (colorState.value != null) {
        animateColorAsState(
            targetValue = Color(colorState.value ?: Note.noteColors.random().toArgb()),
            animationSpec = tween(600)
        ).value
    } else {
        Note.noteColors.random()
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(60.dp),
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    navController.navigateUp()
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note icon")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorAnimation)
                .padding(12.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { focusManager.clearFocus() })
        ) {

            ColorsRow(Color(colorState.value ?: Note.noteColors.random().toArgb())) { newColor ->
                viewModel.onEvent(AddEditNoteEvent.ChangeColor(color = newColor))
            }
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it.isFocused))
                    },
                value = if (noteTitle.isHintVisible) "Enter title here..." else noteTitle.text,
                textStyle = MaterialTheme.typography.h4,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            BasicTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .onFocusChanged {
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it.isFocused))
                    },
                value = if (noteContent.isHintVisible) "Enter content here..." else noteContent.text,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = { viewModel.onEvent(AddEditNoteEvent.EnteredContent(it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
    }
}

@Composable
fun ColorsRow(colorSelected: Color, onChangeColor: (Int) -> Unit) {
    val colorsList = Note.noteColors

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(colorsList) { color ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = 4.dp,
                        color = if (color == colorSelected) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onChangeColor(color.toArgb()) }
            )
        }
    }
}