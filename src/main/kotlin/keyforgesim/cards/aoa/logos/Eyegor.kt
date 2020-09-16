package keyforgesim.cards.aoa.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class Eyegor : Card(
    "Eyegor",
    CardType.CREATURE,
    playValue = 2,
    power = 2,
) {

    override fun playEffect(gameState: GameState) {
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