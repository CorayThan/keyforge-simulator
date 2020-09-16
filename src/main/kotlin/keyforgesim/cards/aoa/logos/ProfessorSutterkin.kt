package keyforgesim.cards.aoa.logos

import keyforgesim.FutureEffect
import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class ProfessorSutterkin : Card(
    "Professor Sutterkin",
    useValue = 2,
    type = CardType.CREATURE,
    power = 2,
) {

    override fun reap(gameState: GameState) {
        super.reap(gameState)
        gameState.drawCards(gameState.findCardsOnBoard(CardType.CREATURE, House.LOGOS).size)
    }

}
