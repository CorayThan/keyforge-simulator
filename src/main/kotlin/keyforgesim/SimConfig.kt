package keyforgesim

val defaultSimConfig = SimConfig(
)

data class SimConfig(
    val damageDealtPerTurn: Int = 1,
    val destroyCreaturesPerTurn: Double = 1.0,
    val creaturesStunnedPerTurn: Double = 1.0,
    val creaturesBouncedPerTurn: Double = 1.0,
    val boardWipesPer10Turns: Double = 1.0,
    val aemberLostPerTurn: Double = 1.0,
    val boardVsCardsPlayed: BoardVsCardsPlayed = BoardVsCardsPlayed.EVEN
)

enum class BoardVsCardsPlayed(val cardsMultiplier: Double) {
    PREFER_BOARD(0.5),
    EVEN(1.0),
    PREFER_CARDS_PLAYED(1.5),
    MAXIMIZE_CARDS_PLAYED(2.0)
}
