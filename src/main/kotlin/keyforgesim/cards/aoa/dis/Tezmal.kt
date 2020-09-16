package keyforgesim.cards.aoa.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class Tezmal : Card(
    "Tezmal",
    CardType.CREATURE,
    useValue = 1,
    power = 2,
) {

    override fun reap(gameState: GameState) {
        super.reap(gameState)
        // TODO
    }

}