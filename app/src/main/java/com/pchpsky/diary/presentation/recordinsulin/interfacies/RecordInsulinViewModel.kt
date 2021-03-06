package com.pchpsky.diary.presentation.recordinsulin.interfacies

import com.pchpsky.diary.data.network.model.Insulin
import com.pchpsky.diary.presentation.recordinsulin.RecordInsulinViewState
import kotlinx.coroutines.flow.StateFlow

interface RecordInsulinViewModel {
    val uiState: StateFlow<RecordInsulinViewState>
    fun incrementUnits()
    fun decrementUnits()
    fun setUnits(points: String)
    suspend fun insulins()
    fun selectInsulin(insulin: Insulin)
    fun dropInsulinMenu(drop: Boolean)
    fun showTimePicker(show: Boolean)
    fun showDatePicker(show: Boolean)
    fun selectTime(localTime: String)
    fun selectDate(localDate: String)
}