package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.coinFlip

class EffervescentPrinciple : Card(
    "Effervescent Principle",
    type = CardType.ACTION,
    playValue = 1,
    amber = -1,
    chains = 1
) {

    override fun playHoldDiscard(gameState: GameState): PlayChoice {
        if (coinFlip()) {
            return PlayChoice.DISCARD
        }
        return PlayChoice.PLAY
    }

    override fun playEffect(gameState: GameState) {
        gameState.currentTurnRecord.amberDestroyed += 3
    }

}
