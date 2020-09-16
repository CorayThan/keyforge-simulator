package keyforgesim.cards.aoa.logos

import keyforgesim.GameState
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder

class Eureka : Card(
    "Eureka!",
    type = CardType.ACTION,
    playOrder = PlayOrder.ALPHA,
    playValue = 4,
    amber = 3
) {

    override fun playEffect(gameState: GameState) {
        repeat(2) {
            if (gameState.hand.isNotEmpty()) {
                gameState.archiveCard(gameState.hand.random())
            }
        }
    }

}
