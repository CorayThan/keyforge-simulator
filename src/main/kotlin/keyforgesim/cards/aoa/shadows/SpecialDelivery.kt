package keyforgesim.cards.aoa.shadows

import keyforgesim.*
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class SpecialDelivery : Card(
    "Special delivery",
    useValue = 1,
    playValue = 1,
    type = CardType.ARTIFACT,
) {
    override fun omni(gameState: GameState) {
        super.omni(gameState)
        gameState.currentTurnRecord.damage += 3
        gameState.destroy(this)
    }
}
