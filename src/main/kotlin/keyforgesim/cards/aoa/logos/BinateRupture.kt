package keyforgesim.cards.aoa.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.cards.PlayOrder
import keyforgesim.coinFlip

class BinateRupture : Card(
    "Binate Rupture",
    type = CardType.ACTION,
    playOrder = PlayOrder.ALPHA,
) {

    var toGain: Int = 0

    override fun playHoldDiscard(gameState: GameState): PlayChoice {
        val opponentAmber = gameState.opponentAmber
        val currentAmber = gameState.currentAmber
        return if (currentAmber > opponentAmber + 1) {
            toGain = currentAmber
            PlayChoice.PLAY
        } else {
            PlayChoice.DISCARD
        }
    }

    override fun playEffect(gameState: GameState) {
        gameState.modifyAmber(toGain)
        toGain = 0
    }

}
