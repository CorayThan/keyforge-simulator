package keyforgesim.cards.aoa.untamed

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder

class PunctuatedEquilibrium : Card(
    "Punctuated Equilibrium",
    type = CardType.ACTION,
    playValue = 4,
    playOrder = PlayOrder.LAST,
) {

    override fun playEffect(gameState: GameState) {
        gameState.discardCards(gameState.hand)
        gameState.fillHand(gameState.handSize)
    }
}