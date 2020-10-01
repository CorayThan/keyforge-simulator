package keyforgesim.cards

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class BlankCard : Card(
    "Blank Card",
    CardType.ACTION,
    playValue = 2
) {

    override fun staticEffect(gameState: GameState) {
    }

}