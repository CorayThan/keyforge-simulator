package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder

class SuckerPunch : Card(
    "Sucker Punch",
    type = CardType.ACTION,
    playOrder = PlayOrder.ALPHA,
    playValue = 2,
    amber = 1,
) {

    override fun playEffect(gameState: GameState) {
        repeat(2) {
            if (gameState.hand.isNotEmpty()) {
                gameState.archiveCard(gameState.hand.random())
            }
        }
    }

}
