package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.odds

class NerveBlast : Card(
    "Nerve Blast",
    type = CardType.ACTION,
    playValue = 2,
) {

    override fun playEffect(gameState: GameState) {
        odds(85) {
            gameState.currentTurnRecord.steal += 1
            gameState.currentTurnRecord.damage += 2
        }
    }

}
