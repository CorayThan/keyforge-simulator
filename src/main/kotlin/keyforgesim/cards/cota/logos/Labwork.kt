package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class Labwork : Card(
    "Labwork",
    type = CardType.ACTION,
    playValue = 2,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        gameState.archiveCardFromHand(house)
    }

}
