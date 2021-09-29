package com.pchpsky.diary.screens.settings.interfaces

import com.pchpsky.diary.screens.settings.SettingsState
import kotlinx.coroutines.flow.StateFlow

interface InsulinSettings {
    val uiState: StateFlow<SettingsState>

    suspend fun addInsulin(color: String, name: String)
}