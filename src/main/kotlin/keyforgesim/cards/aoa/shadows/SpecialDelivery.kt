package keyforgesim.cards.aoa.shadows

import keyforgesim.*
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class SpecialDelivery : Card(
    "Special delivery",
    useValue = 1,
    playValue = 1,
    type = CardType.ARTIFACT,
    hasOmni = true,
) {
    override fun omni(gameState: GameState) {
        gameState.currentTurnRecord.damage += 3
        gameState.destroy(this)
    }
}
