package keyforgesim

data class TurnRecord(
    var house: House = House.DIS,

    var amber: Int = 0,
    var steal: Int = 0,
    var captured: Int = 0,
    var amberDestroyed: Int = 0,

    var damage: Int = 0,
    var creaturesDestroyed: Int = 0,
    var creaturesBounced: Int = 0,
    var creaturesStunned: Int = 0,

    var cardsDrawn: Int = 0,
    var cardsDiscarded: Int = 0,
    var archived: Int = 0,
    var archivedRandomly: Int = 0,
    var shuffles: Int = 0,
    var chainsGained: Int = 0,
    var wentInfinite: Boolean = false,
    var opponentKeyCost: Int = 6,
    var playerKeyCost: Int = 6,
    var played: List<String> = listOf(),
    var gotBrig: Boolean = false,
)
