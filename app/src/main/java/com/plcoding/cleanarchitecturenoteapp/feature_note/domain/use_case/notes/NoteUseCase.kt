package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.notes

data class NoteUseCase(
    val getNotes : GetNotesUseCase,
    val deleteNote : DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteById : GetNoteUseCase,
)
