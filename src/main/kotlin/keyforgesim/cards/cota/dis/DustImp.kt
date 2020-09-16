package keyforgesim.cards.cota.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class DustImp : Card(
    "Dust Imp",
    CardType.CREATURE,
    playValue = 2,
    power = 2,
) {

    override fun destroy(gameState: GameState) {
        super.destroy(gameState)
        gameState.modifyAmber(2)
    }

}
