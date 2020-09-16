package keyforgesim.cards.cota.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class Fear : Card(
    "Fear",
    CardType.ACTION,
    playValue = 1,
) {

    override fun playEffect(gameState: GameState) {
        gameState.currentTurnRecord.creaturesBounced++
    }

}
