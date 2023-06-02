package com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.PreferencesRepositoryImpl.PrefsKeys.IS_DESCENDING_KEY
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.PreferencesRepositoryImpl.PrefsKeys.SORT_ORDER_KEY
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.mappers.toNoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.mappers.toNoteOrderPref
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.NoteOrderPref
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.SortingEnum
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.PreferencesRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesRepository {
    private object PrefsKeys {
        val SORT_ORDER_KEY = intPreferencesKey("sorting_order_key")
        val IS_DESCENDING_KEY = booleanPreferencesKey("is_descending")
    }

    override suspend fun saveOrderPrefs(noteOrder: NoteOrder) {
        val noteOrderPref = noteOrder.toNoteOrderPref()
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[SORT_ORDER_KEY] = noteOrderPref.sortEnum.ordinal
                preferences[IS_DESCENDING_KEY] = noteOrderPref.isDescending
            }
        }
    }

    override suspend fun getOrderPrefs(): Flow<NoteOrder?> {
        // return Result.runCatching {
        val flow = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }

            }.map { preferences ->
                val sortOrderNum = preferences[SORT_ORDER_KEY]
                val isDescending = preferences[IS_DESCENDING_KEY]
                NoteOrderPref(
                    sortEnum = SortingEnum.values()[sortOrderNum ?: 0],
                    isDescending = isDescending ?: false
                ).toNoteOrder()
            }
//        val value = flow.firstOrNull()
//        emit(value)
        return flow
    }
}