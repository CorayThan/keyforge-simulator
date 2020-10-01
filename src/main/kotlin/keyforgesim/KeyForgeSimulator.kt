package keyforgesim

val debug = false

val decksToTest = listOf<Deck>()
//    .plus(keyforgesim.getLabworks)
//    .plus(keyforgesim.getSloppyLabworks)
//    .plus(keyforgesim.getYurks)
//    .plus(keyforgesim.getDrawPips)
//    .plus(keyforgesim.getMothers)
//    .plus(sloppyMothers)
//    .plus(keyforgesim.getTimeTraveller)
//    .plus(keyforgesim.getLayOfTheLand)
//    .plus(keyforgesim.getInfoOfficerGray)
//    .plus(keyforgesim.getEyegor)
//    .plus(keyforgesim.getPunctuatedEquilibrium)
//    .plus(keyforgesim.getTautau)
//    .plus(eclecticInquiry)
//    .plus(chineseDeck)
    //.plus(blank)
    .plus(anteaterLike)

fun printDebug(message: () -> String) {
    if (debug) {
        println(message())
    }
}

fun main() {

    decksToTest.forEach { deck ->
        val stats = mutableListOf<GameState>()

        val games = if (debug) 1 else 100000

        repeat(games) {

            val gameState = deck.buildGame()

            val turnsPerGame = 10

            repeat(turnsPerGame) {
                gameState.takeTurn(defaultSimConfig)
            }

            stats.add(gameState)

        }

        println("For deck: ${deck.name}")
        stats.printStats(games)
    }
}

enum class House {
    BROBNAR,
    DIS,
    LOGOS,
    MARS,
    SANCTUM,
    SHADOWS,
    SAURIAN,
    STAR,
    UNTAMED
}
