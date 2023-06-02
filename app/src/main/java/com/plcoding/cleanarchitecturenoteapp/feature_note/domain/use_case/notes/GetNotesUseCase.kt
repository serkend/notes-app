package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.notes

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder) {
                is NoteOrder.Title -> {
                    when (noteOrder.orderType) {
                        OrderType.Ascending -> notes.sortedBy { it.title.lowercase() }
                        OrderType.Descending -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }
                is NoteOrder.Date -> {
                    when (noteOrder.orderType) {
                        OrderType.Ascending -> notes.sortedBy { it.timestamp }
                        OrderType.Descending -> notes.sortedByDescending { it.timestamp }
                    }
                }
                is NoteOrder.Color -> {
                    when (noteOrder.orderType) {
                        OrderType.Ascending -> notes.sortedBy { it.color }
                        OrderType.Descending -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}