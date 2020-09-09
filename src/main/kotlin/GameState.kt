import java.math.RoundingMode

class GameState {
    var hand = listOf<Card>()
    var archived = listOf<Card>()
    var library = listOf<Card>()
    var discard = listOf<Card>()
    var purged = listOf<Card>()

    var shuffles = 0
    var turns = 0

    val cardsPlayed = mutableListOf<Int>()

    fun takeTurn() {
        val handSize = 6

        fillHand(handSize)

        if (turns == 0 && hand.count { it.house == House.ONE } == 2 && hand.count { it.house == House.TWO } == 2) {
            // mulligan
            discardCards(hand)
            shuffleDiscardIntoDeck()
            fillHand(handSize)
        }

        val originalHand = hand
        val originalDiscard = discard

        val housesMapped = hand.groupBy { it.house }.map { it.value.size to it.key }
        val most = housesMapped.map { it.first }.maxOrNull()!!
        val houseOptions = housesMapped.filter { it.first == most }.map { it.second }
        val house = if (houseOptions.size == 1) {
            houseOptions.first()
        } else {
            val libraryToHouse =
                houseOptions.map { house -> library.count { it.house == house } to house }.toMap()
            val fewestInLibrary = libraryToHouse.keys.minOrNull()!!
            libraryToHouse[fewestInLibrary] ?: error("shouldn't be empty")
        }

        if (archived.any { it.house == house }) {
            if (debug) println("Took archives: ${archived} with hand: ${handString()}")
            takeArchives()
        }

        var played = 0
        do {
            val toPlay = cardInHandOfHouse(house)

            if (toPlay != null) {
                played++
                toPlay.effects(this, house)
                discardCard(toPlay)
            }

        } while (toPlay != null)

        cardsPlayed.add(played)

        turns++

        if (debug) println("Choose house: $house hand: ${originalHand.contentsToString()} discard: ${originalDiscard.contentsToString()} played cards: $played")
    }

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
