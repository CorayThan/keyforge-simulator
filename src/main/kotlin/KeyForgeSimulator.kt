import java.math.RoundingMode

val debug = false

data class Deck(val name: String, val cards: List<Card>)

val decksToTest = listOf<Deck>()
    //.plus(labworks)
    .plus(sloppyLabworks)
//    .plus(yurks)


fun main() {

    decksToTest.forEach { deck ->
        val stats = mutableListOf<GameState>()

        val games = if (debug) 1 else 1000000

        repeat(games) {

            val gameState = buildGame(deck)

            val turnsPerGame = 10

            repeat(turnsPerGame) {
                takeTurn(gameState)
            }

            stats.add(gameState)

        }

        println("For deck: ${deck.name}")
        stats.printStats(games)
    }
}

fun takeTurn(gameState: GameState) {
    val handSize = 6

    gameState.fillHand(handSize)

    if (gameState.turns == 0 && gameState.hand.count { it.house == House.ONE } == 2 && gameState.hand.count { it.house == House.TWO } == 2) {
        // mulligan
        gameState.discardCards(gameState.hand)
        gameState.shuffleDiscardIntoDeck()
        gameState.fillHand(handSize)
    }

    val originalHand = gameState.hand
    val originalDiscard = gameState.discard

    val housesMapped = gameState.hand.groupBy { it.house }.map { it.value.size to it.key }
    val most = housesMapped.map { it.first }.maxOrNull()!!
    val houseOptions = housesMapped.filter { it.first == most }.map { it.second }
    val house = if (houseOptions.size == 1) {
        houseOptions.first()
    } else {
//        houseOptions.first()
        val libraryToHouse =
            houseOptions.map { house -> gameState.library.count { it.house == house } to house }.toMap()
        val fewestInLibrary = libraryToHouse.keys.minOrNull()!!
        libraryToHouse[fewestInLibrary] ?: error("shouldn't be empty")
    }


    if (gameState.archived.any { it.house == house }) {
        if (debug) println("Took archives: ${gameState.archived} with hand: ${gameState.handString()}")
        gameState.takeArchives()
    }

    val cardsByHouse = gameState.hand.groupBy { it.house }

    var played = 0
    do {
        val toPlay = gameState.cardInHandOfHouse(house)

        if (toPlay != null) {
            played++
            toPlay.effects(gameState, house)
            gameState.discardCard(toPlay)
        }

    } while (toPlay != null)

    gameState.cardsPlayed.add(played)

    gameState.turns++

    if (debug) println("Choose house: $house hand: ${originalHand.contentsToString()} discard: ${originalDiscard.contentsToString()} played cards: $played")
}


fun buildGame(deck: Deck): GameState {
    val cards = deck.cards
    val houseOneToAdd = 12 - cards.count { it.house == House.ONE } - 1
    val houseTwoToAdd = 12 - cards.count { it.house == House.TWO } - 1
    val houseThreeToAdd = 12 - cards.count { it.house == House.THREE } - 1
    val library = cards
        .plus((0..houseOneToAdd).map { VanillaCard(House.ONE) })
        .plus((0..houseTwoToAdd).map { VanillaCard(House.TWO) })
        .plus((0..houseThreeToAdd).map { VanillaCard(House.THREE) })
        .shuffled()

    val gameState = GameState()
    gameState.library = library
    return gameState
}

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

enum class House {
    ONE,
    TWO,
    THREE
}

class GameState {
    var hand = listOf<Card>()
    var archived = listOf<Card>()
    var library = listOf<Card>()
    var discard = listOf<Card>()
    var purged = listOf<Card>()

    var shuffles = 0
    var turns = 0

    val cardsPlayed = mutableListOf<Int>()

    fun cardInHandOfHouse(house: House): Card? {
        return hand.sortedBy { it.playLast }.reversed().firstOrNull { it.house == house }
    }

    fun discardCard(card: Card) {
        hand = hand.minus(card)
        discard = discard.plus(card)
    }

    fun discardCards(cards: List<Card>) {
        hand = hand.minus(cards)
        discard = discard.plus(cards)
    }

    fun fillHand(maxCards: Int) {
        val add = maxCards - hand.size
        drawCards(add)
    }

    fun shuffleDiscardIntoDeck() {
        library = library.plus(discard).shuffled()
        discard = listOf()
    }

    fun archiveCard(card: Card) {
        hand = hand.minus(card)
        discard = discard.minus(card)
        library = library.minus(card)
        purged = purged.minus(card)
        archived = archived.plus(card)
    }

    fun drawCards(num: Int) {
        repeat(num) {
            if (library.isEmpty()) {
                shuffles++
                shuffleDiscardIntoDeck()
            }

            hand = hand.plus(library.first())
            library = library.minus(library.first())
        }
    }

    fun takeArchives() {
        hand = hand.plus(archived)
        archived = listOf()
    }

    fun printStats() {
        println("Average cards per turn ${cardsPlayed.average()} shuffles $shuffles turns $turns")
    }

    fun handString(): String {
        return "Hand contains: ${hand.groupBy { it.house }.map { "${it.key}=${it.value.size}" }}"
    }
}

fun List<GameState>.printStats(games: Int) {

    val played = this.map { it.cardsPlayed.average() }.average()
    val shuffles = this.map { it.shuffles }.average()
    val turns = this.map { it.turns }.average()

    println(
        """
        Average cards per turn: ${played.toBigDecimal().setScale(2, RoundingMode.HALF_UP)}         
        extra cards played: ${((played - 3.33) * turns).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}
    """.trimIndent()
//        shuffles $shuffles
//        turns $turns
//        games $games
    )
}

fun List<Card>.contentsToString(): String {
    return "${this.groupBy { it.house }.map { "${it.key}=${it.value.size}" }}"
}
