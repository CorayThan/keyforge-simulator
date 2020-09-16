package keyforgesim.cards.cota.logos

import keyforgesim.FutureEffect
import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class DocBookton : Card(
    "Doc Bookton",
    useValue = 1,
    type = CardType.CREATURE,
) {

    override fun reap(gameState: GameState) {
        super.reap(gameState)
        gameState.drawCards(1)
    }

}
