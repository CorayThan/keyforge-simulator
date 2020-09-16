package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class BrendTheFanatic : Card(
    "Brend the Fanatic",
    CardType.CREATURE,
    playValue = 2,
    power = 3,
) {

    override fun playEffect(gameState: GameState) {
        super.playEffect(gameState)
        // TODO
    }

    override fun destroy(gameState: GameState) {
        super.destroy(gameState)
        gameState.modifyAmber(2)
    }

}
