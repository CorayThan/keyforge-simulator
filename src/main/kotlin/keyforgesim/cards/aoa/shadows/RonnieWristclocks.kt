package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class RonnieWristclocks : Card(
    "Ronnie Wristclocks",
    CardType.CREATURE,
    playValue = 3,
    power = 2,
) {

    override fun playEffect(gameState: GameState) {
        super.playEffect(gameState)
        gameState.steal(if (gameState.opponentAmber > 6) 2 else 1)
    }

}
