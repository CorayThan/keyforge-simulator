package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class Sneklifter : Card(
    "Sneklifter",
    CardType.CREATURE,
    playValue = 2,
    power = 2,
) {

    override fun playEffect(gameState: GameState) {
        super.playEffect(gameState)
        // TODO
    }

}
