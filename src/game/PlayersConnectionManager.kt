package com.example.game

import com.google.inject.Singleton
import enums.SocketEventsServer
import game.helper.streamForAll
import game.objects.Player
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.net.SocketException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer

@Singleton
class PlayersConnectionManager {
    private var playersNumber = 0
    val clients : ArrayList<Player> = arrayListOf()
    var isReady = false
    var matchReady = false
    private lateinit var timer : Timer

    //Game params
    private var time = 10000
    private val minimumPlayers = 12
    private val maximumPlayers = 100



    suspend fun start(){
        timer = fixedRateTimer(name="game-match-timer",period = 1000){
            GlobalScope.launch {
                timerTask()
            }
        }
    }

    private suspend fun timerTask(){
        time--
        clients.streamForAll(SocketEventsServer.TIME_UPDATE.code,time)
        println("Tempo para começar a partida:$time")
        println("Jogadores $playersNumber")
        try{
            if(playersNumber == maximumPlayers){
                matchStart()
            }
            if(time == 0 && playersNumber > minimumPlayers){
                matchStart()
            }
            if(time == 0 && playersNumber < minimumPlayers){
                killSockets()
                matchCannotStart()
            }

        }
        catch (err : SocketException){
            println(err.message)
        }

    }

    fun matchStart(){
        timer.cancel()
        isReady = true
        matchReady = true
    }

    fun matchCannotStart(){
        isReady = true
        matchReady = false
        timer.cancel()
        println("Servidor encerrando, não ha jogadores o suficiente")
    }

    fun playerConnected(client : DefaultWebSocketServerSession){
        playersNumber++
        val clientT = Player(UUID.randomUUID(),"",client, arrayOf(), arrayOf())
        clients.add(clientT)
    }

    fun playerDisconnected(){
        playersNumber--
    }

    suspend fun killSockets(){
        clients.forEach { it.clientSocket.close(CloseReason(CloseReason.Codes.GOING_AWAY,"Server is shuting down")) }
    }

    private fun write(message: Any,writer : PrintWriter) {
        writer.print(message)
        writer.print('\r')
        writer.print('\n')
    }

}