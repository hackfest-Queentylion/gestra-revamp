package com.queentylion.gestra.domain.ble

import com.queentylion.gestra.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface GloveReceiveManager {

    val data: MutableSharedFlow<Resource<GloveResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}