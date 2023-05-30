package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {

    @Query("select * from note_table")
    fun getAllNotes(): Flow<List<Note>>

    @Query("select * from note_table where id = :id")
    suspend fun getNoteById(id:Int) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)
}