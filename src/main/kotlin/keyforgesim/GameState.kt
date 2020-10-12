package keyforgesim

import keyforgesim.cards.*
import java.math.RoundingMode
import kotlin.random.Random

data class FutureEffect(
    var onTurn: Int? = null,
    val repeatInTurns: Int? = null,
    val house: House? = null,
    val effect: () -> Unit,
)

class GameState {
    var hand = listOf<Card>()
    var archived = listOf<Card>()
    var library = listOf<Card>()
    var discard = listOf<Card>()
    var board = listOf<Card>()
    var purged = listOf<Card>()

    val turnRecords = mutableListOf<TurnRecord>()

    var handSize = 6

    var chains = 0

    val futureEffects = mutableListOf<FutureEffect>()

    val currentTurnRecord: TurnRecord
        get() = turnRecords.last()

    val currentHouse: House
        get() = currentTurnRecord.house

    val turns: Int
        get() = turnRecords.size

    val shuffles: Int
        get() = turnRecords.sumBy { it.shuffles }

    val hasCreature: Boolean
        get() = board.any { it.type == CardType.CREATURE }

    val opponentAmber: Int
        get() = Random.nextInt(8)

    val currentAmber: Int
        get() = Random.nextInt(5)

    fun takeTurn(config: SimConfig) {

        turnRecords.add(TurnRecord())

        fillHand()

        if (turns == 0) {
            if (hand.groupBy { it.house }.all { it.value.size == 2 }) {
                // mulligan
                discardCards(hand)
                shuffleDiscardIntoDeck()

                currentTurnRecord.cardsDrawn = 0
                currentTurnRecord.cardsDiscarded = 0

                fillHand(handSize - 1)
            }
        }

        var halfOfBrig: Card? = null

        if (hand.any { it.name == "Binate Rupture" } && hand.any { it.name == "Interdimensional Graft" }) {
            currentTurnRecord.gotBrig = true
        } else {
            halfOfBrig = hand.find { it.name == "Binate Rupture" || it.name == "Interdimensional Graft" }
            if (halfOfBrig != null) {
                hand = hand.minus(halfOfBrig)
            }
        }


        val originalHand = hand
        val originalDiscard = discard

        val housesMapped = hand.groupBy { it.house }.map { it.value.size to it.key }
        val most = housesMapped.map { it.first }.maxOrNull()!!
        val houseOptions = housesMapped.filter { it.first == most }.map { it.second }
        val mostCardsHouse = if (houseOptions.size == 1) {
            houseOptions.first()
        } else {
            val libraryToHouse =
                houseOptions.map { house -> library.count { it.house == house } to house }.toMap()
            val fewestInLibrary = libraryToHouse.keys.minOrNull()!!
            libraryToHouse[fewestInLibrary] ?: error("shouldn't be empty")
        }

        val houseChoiceValue = hand
            .groupBy { it.house }
            .map { houseCardsMap ->
                val cardPlayValue = houseCardsMap.value.map { it.playValue + it.useValue + it.staticValue }.sum()
                val boardValue = board
                    .filter { houseCardsMap.key == it.house }
                    .map { it.useValue + (if (it.type == CardType.CREATURE) 1 else 0) }
                    .sum()
                val totalValue =
                    (houseCardsMap.value.size * config.boardVsCardsPlayed.cardsMultiplier) + cardPlayValue + boardValue
                houseCardsMap.key to totalValue
            }
            .maxByOrNull { it.second }!!
            .first

        val house = when (config.boardVsCardsPlayed) {
            BoardVsCardsPlayed.MAXIMIZE_CARDS_PLAYED -> mostCardsHouse
            else -> houseChoiceValue
        }

        currentTurnRecord.house = house

        if (archived.any { it.house == house }) {
            printDebug {
                "Took archives: ${archived} with hand: ${handString()}"
            }
            takeArchives()
        }

        val alphas = hand.filter { it.playOrder == PlayOrder.ALPHA && it.house == house }

        if (alphas.size > 1) {
            val best = alphas.maxByOrNull { it.totalValue }
            if (best != null) {
                currentTurnRecord.played = currentTurnRecord.played.plus(best.name)
                playCard(best)
            }
        }

        do {
            val toPlay = cardInHandOfHouse(house)

            if (toPlay != null) {

                when (toPlay.playHoldDiscard(this)) {
                    PlayChoice.DISCARD -> {
                        discardCard(toPlay)
                    }
                    PlayChoice.HOLD -> {

                    }
                    PlayChoice.PLAY -> {
                        if (toPlay.playOrder == PlayOrder.ALPHA) {
                            discardCard(toPlay)
                        } else {
                            currentTurnRecord.played = currentTurnRecord.played.plus(toPlay.name)
                            playCard(toPlay)
                        }
                    }
                }

            }

        } while (toPlay != null)

        if (halfOfBrig != null) {
            hand = hand.plus(halfOfBrig)
        }

        board.forEach {
            if (board.contains(it)) {
                it.staticEffect(this)
            }
            when (it.type) {
                CardType.CREATURE -> {
                    odds(35) {
                        destroy(it)
                    }
                    if (board.contains(it) && it.ready) {
                        if (it.house == house) {
                            it.use(this)
                        } else if (it.hasOmni) {
                            it.omni(this)
                        }
                    }
                }
                CardType.ARTIFACT -> {
                    odds(10) {
                        destroy(it)
                    }
                    if (board.contains(it)) {

                        if (it.house == house) {
                            it.use(this)
                        } else if (it.hasOmni) {
                            it.omni(this)
                        }
                    }
                }
                CardType.UPGRADE -> {
                    odds(1) {
                        destroy(it)
                    }
                }
                else -> {
                }
            }
        }

        futureEffects.forEach {

            if (house == it.house || turns == it.onTurn) {
                it.effect()
                if (it.repeatInTurns != null && it.onTurn != null) {
                    it.onTurn = (it.onTurn ?: 0) + it.repeatInTurns
                }
            }
        }

        board.forEach { it.ready = true }

        if (debug) {
            println(
                "Choose house: $house \n" +
                        "hand: ${originalHand.contentsToString()} \n" +
                        "contents: ${originalHand.sortedBy { it.house }.joinToString(", ") { it.name }}\n" +
                        "discard: ${originalDiscard.contentsToString()} \n" +
                        "played ${currentTurnRecord.played.size} cards: ${currentTurnRecord.played.joinToString(", ")}\n\n"
            )
        }
    }

    fun playCard(toPlay: Card) {
        drawCards(toPlay.draw)
        currentTurnRecord.amber += toPlay.amber
        currentTurnRecord.captured += toPlay.capturePips
        currentTurnRecord.damage += toPlay.damagePips
        chains += toPlay.chains
        currentTurnRecord.chainsGained += toPlay.chains

        if (toPlay.type.playsToBoard) {
            putOnBoard(toPlay)
        } else {
            discardCard(toPlay)
        }
        toPlay.playEffect(this)
    }

    fun findCreatureSquishiest(): Card? {
        return board.filter { it.type == CardType.CREATURE }.minByOrNull { it.power + it.armor }
    }

    fun attemptToCapture(card: Card, amount: Int) {
        card.capturedAmber += 3
        currentTurnRecord.captured += 3
    }

    fun steal(count: Int) {
        currentTurnRecord.steal += count
        currentTurnRecord.amber += count

    }

    fun modifyAmber(count: Int) {
        currentTurnRecord.amber += count
    }

    fun modifyChains(count: Int) {
        currentTurnRecord.chainsGained += count
        chains += count
    }

    fun destroy(card: Card) {
        printDebug {
            "Destroying ${card.name}"
        }
        board = board.minus(card)
        discard = discard.plus(card)
        card.destroy(this)
        card.leavesPlay(this)
    }

    fun cardInHandOfHouse(house: House): Card? {
        return hand.sortedBy { it.playOrder }.firstOrNull { it.house == house }
    }

    fun discardCard(card: Card) {
        discardCards(listOf(card))
    }

    fun putOnBoard(card: Card) {
        hand = hand.minus(card)
        board = board.plus(card)
    }

    fun isOnBoard(card: Card) = board.contains(card)

    fun discardCards(cards: List<Card>) {
        hand = hand.minus(cards)
        discard = discard.plus(cards)
        currentTurnRecord.cardsDiscarded += cards.size
    }

    fun fillHand(maxHand: Int = handSize) {

        val drawReduction = when (chains) {
            0 -> 0
            in 1..6 -> 1
            in 7..12 -> 2
            in 13..18 -> 3
            else -> 4
        }

        val add = maxHand - hand.size - drawReduction
        if (chains > 0) {
            chains--
        }

        if (add > 0) {
            drawCards(add)
        }
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
        currentTurnRecord.archived++
    }

    fun drawCards(num: Int) {
        repeat(num) {
            if (library.isEmpty()) {
                currentTurnRecord.shuffles++
                shuffleDiscardIntoDeck()
            }

            if (library.isNotEmpty() && !currentTurnRecord.wentInfinite) {
                hand = hand.plus(library.first())
                library = library.minus(library.first())
                currentTurnRecord.cardsDrawn++
                if (currentTurnRecord.cardsDrawn > 100) {
                    currentTurnRecord.wentInfinite = true
                }
            }
        }
    }

    fun addFutureEffect(effect: FutureEffect) {
        this.futureEffects.add(effect)
    }

    fun takeArchives() {
        hand = hand.plus(archived)
        archived = listOf()
    }

    fun handString(): String {
        return "Hand contains: ${hand.groupBy { it.house }.map { "${it.key}=${it.value.size}" }}"
    }

    fun moveToHand(move: Card?) {
        if (move != null) {
            hand = hand.plus(move)
            library = library.minus(move)
            discard = discard.minus(move)
            archived = archived.minus(move)
            purged = purged.minus(move)
        }
    }

    fun archiveTopDeck(num: Int) {
        val toArchive = library.take(num)
        library = library.minus(toArchive)
        archived = archived.plus(toArchive)
        currentTurnRecord.archived++
    }

    fun findCardsOnBoard(type: CardType? = null, house: House? = null): List<Card> {
        return board.filter { (type == null || it.type == type) && (house == null || it.house == house) }
    }

    fun archiveCardFromHand(options: List<Card> = hand.filter { it.house != currentHouse }) {
        if (options.isNotEmpty()) {
            val toArchive = if (archived.isEmpty()) {
                options.oneOfCardsWithLeastOfHouse()
            } else {
                val optionHouses = options.map { it.house }.toSet().toList()
                if (optionHouses.size == 1) {
                    options.first()
                } else {
                    val firstHouseArchiveCount = archived.count { it.house == optionHouses.first() }
                    val secondHouseArchiveCount = archived.count { it.house == optionHouses[1] }
                    if (firstHouseArchiveCount == secondHouseArchiveCount) {
                        options.oneOfCardsWithLeastOfHouse()
                    } else {
                        options.first { it.house == if (firstHouseArchiveCount > secondHouseArchiveCount) optionHouses.first() else optionHouses[1] }
                    }
                }
            }
            printDebug {
                "Archived card of house ${toArchive?.house} from hand: ${handString()}"
            }

            if (toArchive != null) {
                archiveCard(toArchive)
            }
        }
    }
}

fun List<GameState>.printStats(games: Int) {

    val played = this.map { it.turnRecords.map { record -> record.played.size }.average() }.average()
    val shuffles = this.map { it.shuffles }.average()
    val turns = this.map { it.turns }.average()

    println()

//    val brigTurnPercents = this.map {
//        it.turnRecords.indexOfFirst { it.gotBrig }
//    }
//        .groupBy { it }
//        .map { it.key to it.value.size }
//        .sortedBy { if (it.first == -1) 1000 else it.first }
//        .map { "Turn: ${if (it.first == -1) "No Brig" else (it.first + 1)} -- ${((it.second.toDouble() / games) * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%" }
//        .joinToString("\n")

    println(
        """
        Turns: $turns
        Cards Played: ${(played * 10)}         
        Average cards per turn: ${played.toBigDecimal().setScale(2, RoundingMode.HALF_UP)}         
        extra cards played: ${((played - 3.54) * turns).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}
    """.trimIndent()
//        shuffles $shuffles
//        turns $turns
//        games $games
    )


    println()
}
