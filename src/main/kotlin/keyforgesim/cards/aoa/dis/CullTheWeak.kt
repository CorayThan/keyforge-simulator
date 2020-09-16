package keyforgesim.cards.aoa.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.coinFlip
import keyforgesim.odds

class CullTheWeak : Card(
    "Cull the Weak",
    CardType.ACTION,
    playValue = 2,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        odds(75) {
            gameState.currentTurnRecord.creaturesDestroyed++
        }
    }

}
