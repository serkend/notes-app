package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _noteTitle: MutableStateFlow<NoteTextFieldState> =
        MutableStateFlow(NoteTextFieldState())
    val noteTitle = _noteTitle.asStateFlow()

    private var _noteContent: MutableStateFlow<NoteTextFieldState> =
        MutableStateFlow(NoteTextFieldState())
    val noteContent = _noteContent.asStateFlow()

    private var _noteColor: MutableStateFlow<Int?> =
        MutableStateFlow(null)
    val noteColor = _noteColor.asStateFlow()

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            Log.e("TAG", "noteId: $noteId")
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCase.getNoteById(noteId)?.also { note ->
                        Log.e("TAG", "noteId: $note")
                        currentNoteId = note.id
                        _noteTitle.value =
                            _noteTitle.value.copy(text = note.title, isHintVisible = note.title.isEmpty())
                        _noteContent.value =
                            _noteContent.value.copy(text = note.content, isHintVisible = note.content.isEmpty())
                        _noteColor.value = note.color
                    }
                }
            } else {
                _noteColor.value = Note.noteColors.random().toArgb()
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value =
                    noteTitle.value.copy(text = event.value)
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value =
                    _noteContent.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value =
                    noteTitle.value.copy(isHintVisible = !event.focusState && noteTitle.value.text.isEmpty())
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value =
                    _noteContent.value.copy(isHintVisible = !event.focusState && _noteContent.value.text.isEmpty())
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    noteUseCase.addNoteUseCase(
                        Note(
                            title = _noteTitle.value.text,
                            content = _noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = _noteColor.value ?: Note.noteColors.random().toArgb(),
                            id = currentNoteId
                        )
                    )
                }
            }
        }
    }

}