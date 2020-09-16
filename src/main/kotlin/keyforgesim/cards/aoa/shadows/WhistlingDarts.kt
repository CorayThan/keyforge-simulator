package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.odds
import kotlin.random.Random

class WhistlingDarts : Card(
    "Whistling Darts",
    type = CardType.ACTION,
    playValue = 2,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        gameState.currentTurnRecord.damage += Random.nextInt(4)
    }

}
