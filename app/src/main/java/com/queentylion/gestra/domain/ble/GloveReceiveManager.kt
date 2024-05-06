package com.queentylion.gestra.domain.ble

import com.queentylion.gestra.util.ConnectionState
import com.queentylion.gestra.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.ArrayDeque

interface GloveReceiveManager {

    val data: MutableSharedFlow<Resource<GloveResult>>

    var connectionState: ConnectionState

    val flexResistenceArray: ArrayDeque<IntArray>

    var initializingMessage: String?

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}