package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class TimeTraveller : Card(
    "Time Traveller",
    type = CardType.CREATURE,
    playValue = 3,
    draw = 2,
    amber = 1
) {

    var playedOnTurn = -1

    override fun playEffect(gameState: GameState) {
        playedOnTurn = gameState.turns
    }
}