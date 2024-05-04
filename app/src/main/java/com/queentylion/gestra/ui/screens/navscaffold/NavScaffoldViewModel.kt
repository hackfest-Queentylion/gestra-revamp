package com.queentylion.gestra.ui.screens.navscaffold

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NavigationScaffoldState(
    val currentDestinationLabel: String = "Home"
)

class NavScaffoldViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationScaffoldState())
    val uiState : StateFlow<NavigationScaffoldState> = _uiState.asStateFlow()

    fun setNavigationScreen(label: String) {
        _uiState.value = NavigationScaffoldState(label)
    }
}