package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.note_order.NoteOrderUseCases
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.notes.NoteUseCase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NotesEvent
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCase,
    private val noteOrderUseCases: NoteOrderUseCases,
) : ViewModel() {

    private var _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState> = _notesState

    private var recentlyDeletedNote: Note? = null

    init {
        getNoteOrder()
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                }
            }
            is NotesEvent.Order -> {
                if (notesState.value.noteOrder::class == event.noteOrder::class &&
                    notesState.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                //  _notesState.value = _notesState.value.copy(noteOrder = event.noteOrder)
                saveNoteOrder(event.noteOrder)
                getNotes(event.noteOrder)
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let { recentNote ->
                        noteUseCases.addNoteUseCase.invoke(recentNote)
                    }
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _notesState.value =
                    _notesState.value.copy(isOrderSectionVisible = !_notesState.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        viewModelScope.launch {
            noteUseCases.getNotes(noteOrder).collect {
                _notesState.value = _notesState.value.copy(notes = it)
            }
        }
    }

    private fun getNoteOrder() {
        viewModelScope.launch {
            noteOrderUseCases.getNoteOrder().collect { noteOrder ->
                noteOrder?.let {
                    _notesState.value = _notesState.value.copy(noteOrder = it)
                }
            }
        }
    }

    private fun saveNoteOrder(noteOrder: NoteOrder) {
        viewModelScope.launch {
            noteOrderUseCases.saveNoteOrder(noteOrder = noteOrder)
        }
    }
}