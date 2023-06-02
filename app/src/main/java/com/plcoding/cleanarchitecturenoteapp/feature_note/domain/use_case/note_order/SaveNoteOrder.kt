package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.note_order

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.PreferencesRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import javax.inject.Inject

class SaveNoteOrder @Inject constructor(private val repository: PreferencesRepository) {
    suspend operator fun invoke(noteOrder:NoteOrder) {
        repository.saveOrderPrefs(noteOrder)
    }
}