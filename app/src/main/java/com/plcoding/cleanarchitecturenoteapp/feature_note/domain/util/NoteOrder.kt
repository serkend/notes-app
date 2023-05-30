package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    fun copy(sortType: OrderType = orderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType = sortType)
            is Date -> Date(orderType = sortType)
            is Color -> Color(orderType = sortType)
        }
    }
}
