package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.mappers

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.NoteOrderPref
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.SortingEnum
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

fun NoteOrderPref.toNoteOrder(): NoteOrder {
    val orderType = if (this.isDescending) OrderType.Descending else OrderType.Ascending
    return when (this.sortEnum) {
        SortingEnum.DATE -> {
            return NoteOrder.Date(orderType)
        }
        SortingEnum.COLOR -> {
            return NoteOrder.Color(orderType)
        }
        SortingEnum.TITLE -> {
            return NoteOrder.Title(orderType)
        }
    }
}