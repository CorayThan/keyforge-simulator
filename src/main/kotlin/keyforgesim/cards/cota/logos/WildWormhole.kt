package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder

class WildWormhole : Card(
    "Wild Wormhole",
    type = CardType.ACTION,
    playValue = 2,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        if (gameState.library.isNotEmpty()) {
            val toPlay = gameState.library.first()
            if (toPlay.playOrder != PlayOrder.ALPHA) {
                gameState.library = gameState.library.minus(toPlay)
                gameState.playCard(toPlay)
            }
        }
    }

}
