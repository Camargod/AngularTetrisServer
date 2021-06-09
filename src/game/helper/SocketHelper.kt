package game.helper

import game.objects.Player
import io.ktor.http.cio.websocket.*


suspend fun DefaultWebSocketSession.sendMessage(eventCode : Int, message : Any){
    this.send("$eventCode|$message")
    println("$eventCode|$message")
}

suspend fun ArrayList<Player>.streamForAll(eventCode : Int, message : Any){
    this.forEach {
        it.clientSocket.sendMessage(eventCode, message)
    }
}