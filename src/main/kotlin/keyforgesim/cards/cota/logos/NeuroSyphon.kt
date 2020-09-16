package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.coinFlip

class NeuroSyphon : Card(
    "Neuro Syphon",
    type = CardType.ACTION,
    playValue = 2,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        coinFlip {
            gameState.steal(1)
            gameState.drawCards(1)
        }
    }
}
