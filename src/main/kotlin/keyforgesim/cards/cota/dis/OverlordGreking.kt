package keyforgesim.cards.cota.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class OverlordGreking : Card(
    "Overlord Greking",
    CardType.CREATURE,
    playValue = 1,
    power = 4,
) {

    override fun fight(gameState: GameState) {
        super.fight(gameState)
        // TODO
    }


}