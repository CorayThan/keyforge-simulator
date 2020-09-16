package keyforgesim.cards.cota.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.coinFlip

class HandOfDis : Card(
    "Hand of Dis",
    CardType.ACTION,
    playValue = 1,
) {

    override fun playEffect(gameState: GameState) {
        coinFlip {
            gameState.currentTurnRecord.creaturesDestroyed++
        }
    }

}
