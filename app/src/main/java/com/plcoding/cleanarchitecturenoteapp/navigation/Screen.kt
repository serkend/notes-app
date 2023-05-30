package com.plcoding.cleanarchitecturenoteapp.navigation

sealed class Screen(val route: String) {
    object NotesScreen : Screen(route = "NotesScreen")
    object AddEditNoteScreen : Screen(route = "AddEditNoteScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}