package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveOrderPrefs(noteOrder: NoteOrder)
    suspend fun getOrderPrefs(): Flow<NoteOrder?>
}