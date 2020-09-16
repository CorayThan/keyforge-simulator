package keyforgesim.cards.cota.logos

import keyforgesim.FutureEffect
import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.debug

class Mother : Card(
    "Mother",
    staticValue = 3,
    type = CardType.CREATURE,
    power = 5
) {

    override fun playEffect(gameState: GameState) {
        gameState.handSize++
    }

    override fun leavesPlay(gameState: GameState) {
        super.leavesPlay(gameState)
        gameState.handSize--
    }

}
