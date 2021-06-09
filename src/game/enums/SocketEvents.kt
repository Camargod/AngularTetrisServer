package enums

enum class SocketEventsServer(val code : Int) {
    TIME_UPDATE(201),
    GAME_START(202),
    RECEIVED_DAMAGE(203),
    IN_MATCH_PLAYERS(204),
    DISCONNECTED_BY_SERVER(299)
}

enum class SocketEventsClient(val code : Int) {
    GRID_UPDATE(102),
    PIECE_GRID_UPDATE(103),
    GAME_OVER(104),
}