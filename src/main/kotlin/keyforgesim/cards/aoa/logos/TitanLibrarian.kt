package keyforgesim.cards.aoa.logos

import keyforgesim.FutureEffect
import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.debug

class TitanLibrarian : Card(
    "Titan Librarian",
    staticValue = 2,
    type = CardType.CREATURE,
    power = 4
) {

    override fun endOfTurn(gameState: GameState) {
        super.endOfTurn(gameState)
        gameState.archiveCardFromHand(gameState.hand)
    }

}
