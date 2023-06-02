package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.mappers

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.NoteOrderPref
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.SortingEnum
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

fun NoteOrder.toNoteOrderPref() : NoteOrderPref {
    val isDescending = when(this.orderType) {
        OrderType.Ascending -> false
        OrderType.Descending -> true
    }
    val sortingEnum = when(this){
        is NoteOrder.Color -> {
            SortingEnum.COLOR
        }
        is NoteOrder.Date ->  {
            SortingEnum.DATE
        }
        is NoteOrder.Title -> {
            SortingEnum.TITLE
        }
    }
    return NoteOrderPref(sortEnum = sortingEnum, isDescending = isDescending)
}