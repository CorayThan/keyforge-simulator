package keyforgesim.cards.aoa.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.cards.PlayOrder
import keyforgesim.coinFlip

class CutthroatResearch : Card(
    "Cutthroat Research",
    type = CardType.ACTION,
    playValue = 1,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        if (gameState.opponentAmber > 7) {
            gameState.steal(2)
        }
    }

}
