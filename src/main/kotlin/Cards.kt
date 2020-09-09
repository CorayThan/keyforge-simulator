abstract class Card(
    open val playLast: Boolean = false,
    open val isVanilla: Boolean = false
) {
    abstract val house: House
    abstract fun effects(gameState: GameState, house: House)

    override fun toString(): String {
        return "$house - ${this.javaClass.simpleName}"
    }
}

class VanillaCard(
    override val house: House = House.values().random(),
    override val isVanilla: Boolean = true
) : Card() {

    override fun effects(gameState: GameState, house: House) {
    }

}

class EclecticInquiry(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        gameState.archiveTopDeck(2)
    }
}


class TautauVapors(
    override val house: House = House.values().random(),
    override val playLast: Boolean = true
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        gameState.drawCards(2)
        gameState.archiveCardFromHand(house)
    }
}

class PunctuatedEquilibrium(
    override val house: House = House.values().random(),
    override val playLast: Boolean = true
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        gameState.discardCards(gameState.hand)
        gameState.fillHand(gameState.handSize)
    }
}

class Eyegor(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        val top3 = gameState.library.take(3)
        var sameHouse = top3.find { it.house == house && !it.isVanilla }
        if (sameHouse == null) {
            sameHouse = top3.find { it.house == house }
        }
        if (sameHouse != null) {
            gameState.hand = gameState.hand.plus(sameHouse)
        }
        gameState.library = gameState.library.minus(top3)
        gameState.discard = gameState.discard.plus(top3).let { if (sameHouse != null) it.minus(sameHouse) else it }
    }
}

class LayOfTheLand(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        val top3 = gameState.library.take(3)
        var sameHouse = top3.find { it.house == house && !it.isVanilla }
        if (sameHouse == null) {
            sameHouse = top3.find { it.house == house }
        }
        if (sameHouse != null) {
            gameState.library = listOf(sameHouse)
                .plus(gameState.library.minus(sameHouse))
        }
        gameState.drawCards(1)
    }
}

class HelpFromFutureSelf(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        var ttFound =
            gameState.library.find { it::class.java == TimeTraveller::class.java && (it as TimeTraveller).playedOnTurn != gameState.turns }
        if (ttFound == null) {
            ttFound =
                gameState.discard.find { it::class.java == TimeTraveller::class.java && (it as TimeTraveller).playedOnTurn != gameState.turns }
        }
        gameState.moveToHand(ttFound)
        gameState.shuffleDiscardIntoDeck()
    }

}

class TimeTraveller(
    override val house: House = House.values().random()
) : Card() {

    var playedOnTurn = -1

    override fun effects(gameState: GameState, house: House) {
        gameState.drawCards(2)
        playedOnTurn = gameState.turns
    }
}

class InfoOfficerGray(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        if (debug) println("Played mother on turn ${gameState.turns}")
        gameState.archiveCardFromHand(house)
        gameState.addFutureEffect(FutureEffect(gameState.turns + 3) {
            gameState.archiveCardFromHand(house)
        })
    }

}

class Mother(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        if (debug) println("Played mother on turn ${gameState.turns}")
        gameState.handSize++
        gameState.addFutureEffect(FutureEffect(gameState.turns + 3) {
            gameState.handSize--
        })
    }

}

class DrawPip(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        gameState.drawCards(1)
    }

}

class Labwork(
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {
        gameState.archiveCardFromHand(house)
    }

}

class SloppyLabwork(
    override val house: House = House.values().random(),
    override val playLast: Boolean = true,
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

        gameState.archiveCardFromHand(house)
    }

}

class Yurk(
    override val playLast: Boolean = true,
    override val house: House = House.values().random()
) : Card() {

    override fun effects(gameState: GameState, house: House) {

        val optionsToDiscard = gameState.hand.filter { it.house != house }
        if (optionsToDiscard.isNotEmpty()) {
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
