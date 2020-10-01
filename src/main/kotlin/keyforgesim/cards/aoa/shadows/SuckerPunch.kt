package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder
import keyforgesim.coinFlip

class SuckerPunch : Card(
    "Sucker Punch",
    type = CardType.ACTION,
    playOrder = PlayOrder.ALPHA,
    playValue = 2,
    amber = 1,
) {

    override fun playEffect(gameState: GameState) {
        gameState.currentTurnRecord.damage += 2
        coinFlip {

        }
    }

}
