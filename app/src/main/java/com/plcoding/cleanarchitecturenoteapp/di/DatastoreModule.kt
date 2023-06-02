package com.plcoding.cleanarchitecturenoteapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.PreferencesRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.PreferencesRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.note_order.GetNoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.note_order.NoteOrderUseCases
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.note_order.SaveNoteOrder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
    @Provides
    @Singleton
    fun provideDatastore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("USER_PREFERENCES") })
    }

    @Provides
    @Singleton
    fun provideRepository(datastore: DataStore<Preferences>): PreferencesRepository =
        PreferencesRepositoryImpl(datastore)

    @Provides
    @Singleton
    fun provideOrderUseCase(repository: PreferencesRepository) = NoteOrderUseCases(
        getNoteOrder = GetNoteOrder(repository),
        saveNoteOrder = SaveNoteOrder(repository)
    )
}