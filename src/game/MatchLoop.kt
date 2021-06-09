package com.example.game

import game.objects.Player
import java.net.ServerSocket

class MatchLoop(socket : ServerSocket, manager : PlayersConnectionManager) {
    val server = socket
    val manager = manager
    val player : MutableList<Player> = mutableListOf()

    fun startLoop(){
        //manager.clients.
    }
}