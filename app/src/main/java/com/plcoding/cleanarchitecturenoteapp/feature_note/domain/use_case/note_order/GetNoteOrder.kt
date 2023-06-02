package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.note_order

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.PreferencesRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteOrder @Inject constructor(private val repository: PreferencesRepository) {
    suspend operator fun invoke(): Flow<NoteOrder?> {
        return repository.getOrderPrefs()
    }
}