package keyforgesim.cards.aoa.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class Angwish : Card(
    "Angwish",
    CardType.CREATURE,
    staticValue = 1,
    power = 6,
) {

    override fun staticEffect(gameState: GameState) {
        if (damage > 0) {
            gameState.currentTurnRecord.opponentKeyCost += damage
        }
    }

}