package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.cards.PlayOrder
import keyforgesim.coinFlip
import keyforgesim.odds

class LifeForALife : Card(
    "Life for a Life",
    type = CardType.ACTION,
    playValue = 2,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        val toSac = gameState.findCardsOnBoard(CardType.CREATURE).find { it.name == "Brend the Fanatic" || it.name == "Dust Imp" } ?: gameState.findCreatureSquishiest()
        if (toSac != null) {
            gameState.destroy(toSac)
            gameState.currentTurnRecord.damage += 6
        }
    }

}
