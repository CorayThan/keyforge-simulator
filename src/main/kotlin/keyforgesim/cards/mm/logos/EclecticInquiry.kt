package keyforgesim.cards.mm.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class EclecticInquiry : Card(
    "Eclectic Inquiry",
    playValue = 3,
    type = CardType.ACTION,
) {

    override fun playEffect(gameState: GameState) {
        gameState.archiveTopDeck(2)
    }
}