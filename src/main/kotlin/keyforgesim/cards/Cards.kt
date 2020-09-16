package keyforgesim.cards

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.oneToOneHundred

enum class PlayChoice {
    PLAY,
    HOLD,
    DISCARD
}

enum class UseType {
    REAP,
    FIGHT,
    ACTION
}

enum class PlayOrder {
    ALPHA,
    FIRST,
    MIDDLE,
    LAST,
    OMEGA
}

enum class CardType(val playsToBoard: Boolean) {
    ACTION(false),
    CREATURE(true),
    ARTIFACT(true),
    UPGRADE(true)
}

fun randomCardType() = when (oneToOneHundred()) {
    in 0..3 -> CardType.UPGRADE
    in 4..13 -> CardType.ARTIFACT
    in 14..49 -> CardType.ACTION
    else -> CardType.CREATURE
}

abstract class Card(
    val name: String,
    val type: CardType,
    val playValue: Int = 0,
    val useValue: Int = 0,
    val staticValue: Int = 0,
    val playOrder: PlayOrder = PlayOrder.MIDDLE,
    val isVanilla: Boolean = false,
    val amber: Int = 0,
    val draw: Int = 0,
    val capturePips: Int = 0,
    val damagePips: Int = 0,
    val chains: Int = 0,
    val power: Int = 0,
    val armor: Int = 0,
    var damage: Int = 0,
    var capturedAmber: Int = 0,
    var stunned: Boolean = false,
    var enraged: Boolean = false,
    var warded: Boolean = false,
    val action: ((gameState: GameState) -> Unit)? = null,
    val upgrades: MutableList<Card> = mutableListOf()

) {

    val totalValue: Int
        get() = playValue + useValue + staticValue

    lateinit var house: House

    open fun playHoldDiscard(gameState: GameState): PlayChoice {
        return PlayChoice.PLAY
    }

    open fun playEffect(gameState: GameState) {}

    open fun use(gameState: GameState) {
        if (type == CardType.CREATURE) {
            when {
                stunned -> stunned = false
                enraged -> {
                    enraged = false
                    fight(gameState)
                }
                action != null -> action.invoke(gameState)
                else -> {
                    gameState.currentTurnRecord.amber++
                    reap(gameState)
                }
            }
        } else {
            action?.invoke(gameState)
        }
    }

    open fun reap(gameState: GameState) {
        upgrades.forEach { it.reap(gameState) }
    }

    open fun fight(gameState: GameState) {
        upgrades.forEach { it.fight(gameState) }
    }

    open fun omni(gameState: GameState) {}

    open fun staticEffect(gameState: GameState) {}

    open fun destroy(gameState: GameState) {
        upgrades.forEach {
            gameState.destroy(it)
        }
    }

    open fun leavesPlay(gameState: GameState) {
        upgrades.forEach {
            gameState.board = gameState.board.minus(it)
            gameState.discard = gameState.discard.plus(it)
            it.leavesPlay(gameState)
        }
    }

    open fun chooseUseType(gameState: GameState) {}

    open fun endOfTurn(gameState: GameState) {}

    override fun toString(): String {
        return "$house - ${this.javaClass.simpleName}"
    }

}

class VanillaCard(val fakeHouse: House) : Card(
    "Vanilla",
    randomCardType(),
    isVanilla = true,
) {

    init {
        house = fakeHouse
    }

    override fun playEffect(gameState: GameState) {
    }

}

fun List<Card>.contentsToString(): String {
    return "${this.groupBy { it.house }.map { "${it.key}=${it.value.size}" }}"
}

fun List<Card>.mostCommonHouses(): Set<House> {
    val withHouseCounts = this.groupBy { it.house }.map { it.value.size to it.value }.toMap()
    val max = withHouseCounts.keys.maxOrNull()
    return withHouseCounts.filter { it.key == max }.map { it.value.first().house }.toSet()
}

fun List<Card>.oneOfCardsWithMostOfHouse(): Card? {
    val withHouseCounts = this.groupBy { it.house }.map { it.value.size to it.value }.toMap()
    val max = withHouseCounts.keys.maxOrNull()
    return withHouseCounts[max]?.random()
}

fun List<Card>.oneOfCardsWithLeastOfHouse(): Card? {
    val withHouseCounts = this.groupBy { it.house }.map { it.value.size to it.value }.toMap()
    val min = withHouseCounts.keys.minOrNull()
    return withHouseCounts[min]?.random()
}
