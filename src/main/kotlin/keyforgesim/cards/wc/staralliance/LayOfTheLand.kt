package keyforgesim.cards.wc.staralliance

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class LayOfTheLand : Card(
    "Lay of the Land",
    type = CardType.ACTION,
    playValue = 3,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
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