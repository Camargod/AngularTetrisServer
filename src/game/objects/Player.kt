package game.objects

import io.ktor.websocket.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.PrintWriter
import java.net.Socket
import java.util.*

data class Player(
    val id : UUID,
    val name : String? = "",
    val clientSocket : DefaultWebSocketServerSession,
    var gridPieces : Array<Int>? = arrayOf(),
    var gridActualPiece : Array<Int>,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false
        if (name != other.name) return false
        if (clientSocket != other.clientSocket) return false
        if (!gridPieces.contentEquals(other.gridPieces)) return false
        if (!gridActualPiece.contentEquals(other.gridActualPiece)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + clientSocket.hashCode()
        result = 31 * result + gridPieces.contentHashCode()
        result = 31 * result + gridActualPiece.contentHashCode()
        return result
    }
}