package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.cards.PlayOrder
import keyforgesim.coinFlip

class InterdimensionalGraft : Card(
    "Interdimensional Graft",
    type = CardType.ACTION,
    playValue = 1,
    amber = 1,
    playOrder = PlayOrder.LAST
) {

    override fun playEffect(gameState: GameState) {
        val amountToTake = gameState.opponentAmber - gameState.currentTurnRecord.opponentKeyCost
        if (amountToTake > 0) {
            gameState.modifyAmber(amountToTake)
            gameState.currentTurnRecord.amberDestroyed += amountToTake
        }
    }

}
