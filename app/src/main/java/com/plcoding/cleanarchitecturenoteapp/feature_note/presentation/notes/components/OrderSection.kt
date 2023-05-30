package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    visible: Boolean,
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column() {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Column() {
                Row(modifier = Modifier.fillMaxWidth()) {
                    DefaultRadioButton(
                        text = "Title",
                        selected = noteOrder is NoteOrder.Title,
                        onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DefaultRadioButton(
                        text = "Date",
                        selected = noteOrder is NoteOrder.Date,
                        onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DefaultRadioButton(
                        text = "Color",
                        selected = noteOrder is NoteOrder.Color,
                        onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
                    )
                }
//        Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    DefaultRadioButton(
                        text = "Ascending",
                        selected = noteOrder.orderType is OrderType.Ascending,
                        onSelect = {
                            onOrderChange(noteOrder.copy(sortType = OrderType.Ascending))
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DefaultRadioButton(
                        text = "Descending",
                        selected = noteOrder.orderType is OrderType.Descending,
                        onSelect = { onOrderChange(noteOrder.copy(sortType = OrderType.Descending)) }
                    )
                }
            }
        }
    }
}