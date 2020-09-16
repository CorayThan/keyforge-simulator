package keyforgesim.cards.aoa.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.cards.PlayOrder

class Soulkeeper : Card(
    "Soulkeeper",
    CardType.UPGRADE,
    playValue = 2,
    playOrder = PlayOrder.LAST
) {

    override fun playEffect(gameState: GameState) {
        gameState.findCreatureSquishiest()?.upgrades?.add(this)
    }

    override fun destroy(gameState: GameState) {
        gameState.currentTurnRecord.creaturesDestroyed++
    }

    override fun playHoldDiscard(gameState: GameState): PlayChoice {
        if (gameState.hasCreature) return PlayChoice.PLAY
        return PlayChoice.DISCARD
    }

}
