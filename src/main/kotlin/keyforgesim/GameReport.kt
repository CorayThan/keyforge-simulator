package keyforgesim

data class GameReport(
    val turnsTaken: Double,
    val wentInfinite: Int,
    
    val amber: Double,
    val steal: Double,
    val captured: Double,
    val amberDestroyed: Double,
    
    val damage: Double,
    val creaturesDestroyed: Double,
    val creaturesBounced: Double,
    val creaturesStunned: Double,
    val creaturesEnraged: Double,
    
    val cardsPlayed: Double,
    val cardsDrawn: Double,
    val cardsDiscarded: Double,
    val archived: Double,
    val archivedRandomly: Double,
    val shuffles: Double,
    val chainsGained: Double,


)
