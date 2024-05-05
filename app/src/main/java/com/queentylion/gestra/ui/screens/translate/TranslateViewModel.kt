package com.queentylion.gestra.ui.screens.translate

import androidx.lifecycle.ViewModel
import com.queentylion.gestra.domain.ble.GloveReceiveManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val gloveReceiveManager: GloveReceiveManager
) : ViewModel() {


}