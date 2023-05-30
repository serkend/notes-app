package com.plcoding.cleanarchitecturenoteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
        composable(
            route = Screen.AddEditNoteScreen.route + "/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.IntType
            })
        ) {
            AddEditNoteScreen(navController = navController)
        }
        composable(
            route = Screen.NotesScreen.route
        ) {
            NotesScreen(navController = navController)
        }
    }
}
