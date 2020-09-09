val debug = false

val decksToTest = listOf<Deck>()
    //.plus(labworks)
    .plus(sloppyLabworks)
//    .plus(yurks)


fun main() {

    decksToTest.forEach { deck ->
        val stats = mutableListOf<GameState>()

        val games = if (debug) 1 else 1000000

        repeat(games) {

            val gameState = deck.buildGame()

            val turnsPerGame = 10

            repeat(turnsPerGame) {
                gameState.takeTurn()
            }

            stats.add(gameState)

        }

        println("For deck: ${deck.name}")
        stats.printStats(games)
    }
}

data class Deck(val name: String, val cards: List<Card>) {
    fun buildGame(): GameState {
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
}

enum class House {
    ONE,
    TWO,
    THREE
}
