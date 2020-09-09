abstract class Card(open val playLast: Boolean = false) {
    abstract val house: House
    abstract fun effects(gameState: GameState, house: House)

    override fun toString(): String {
        return "$house - ${this.javaClass.simpleName}"
    }
}

class VanillaCard(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
    }

}

class Labwork(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        val options = gameState.hand.filter { it.house != house }
        if (options.isNotEmpty()) {
            val toArchive = if (gameState.archived.isEmpty()) {
                options.oneOfCardsWithLeastOfHouse()
            } else {
                val optionHouses = options.map { it.house }.toSet().toList()
                if (optionHouses.size == 1) {
                    options.first()
                } else {
                    val firstHouseArchiveCount = gameState.archived.count { it.house == optionHouses.first() }
                    val secondHouseArchiveCount = gameState.archived.count { it.house == optionHouses[1] }
                    if (firstHouseArchiveCount == secondHouseArchiveCount) {
                        options.oneOfCardsWithLeastOfHouse()
                    } else {
                        options.first { it.house == if (firstHouseArchiveCount > secondHouseArchiveCount) optionHouses.first() else optionHouses[1] }
                    }
                }
            }
            if (debug) {
                println("Archived card of house ${toArchive?.house} from hand: ${gameState.handString()}")
            }

            if (toArchive != null) {
                gameState.archiveCard(toArchive)
            }
        }
    }

}

class SloppyLabwork(
    override val playLast: Boolean = true,
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {

        var optionsToDiscard = gameState.hand.filter { it.house != house }
        if (optionsToDiscard.size > 1) {
            if (optionsToDiscard.any { it::class.java == VanillaCard::class.java }) {
                optionsToDiscard = optionsToDiscard.filter { it::class.java == VanillaCard::class.java }
            }
            val discard = optionsToDiscard.oneOfCardsWithLeastOfHouse()

            if (debug) {
                println("Discard card ${discard.toString()} from hand: ${gameState.handString()}")
            }

            if (discard != null) {
                gameState.discardCards(listOf(discard))
            }
        }

        val options = gameState.hand.filter { it.house != house }
        if (options.isNotEmpty()) {
            val toArchive = if (gameState.archived.isEmpty()) {
                options.oneOfCardsWithLeastOfHouse()
            } else {
                val optionHouses = options.map { it.house }.toSet().toList()
                if (optionHouses.size == 1) {
                    options.first()
                } else {
                    val firstHouseArchiveCount = gameState.archived.count { it.house == optionHouses.first() }
                    val secondHouseArchiveCount = gameState.archived.count { it.house == optionHouses[1] }
                    if (firstHouseArchiveCount == secondHouseArchiveCount) {
                        options.oneOfCardsWithMostOfHouse()
                    } else {
                        options.first { it.house == if (firstHouseArchiveCount > secondHouseArchiveCount) optionHouses.first() else optionHouses[1] }
                    }
                }
            }
            if (debug) {
                println("Archived card of house ${toArchive?.house} from hand: ${gameState.handString()}")
            }

            if (toArchive != null) {
                gameState.archiveCard(toArchive)
            }
        }
    }

}

class Yurk(
    override val playLast: Boolean = true,
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {

        val optionsToDiscard = gameState.hand.filter { it.house != house }
        if (optionsToDiscard.size > 1) {
            val discard = optionsToDiscard.oneOfCardsWithLeastOfHouse()

            if (debug) {
                println("Discard card ${discard.toString()} from hand: ${gameState.handString()}")
            }

            if (discard != null) {
                gameState.discardCards(listOf(discard))
            }
        }
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
