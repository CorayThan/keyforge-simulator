package keyforgesim.cards.cota.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice

class CollarOfSubordination : Card(
    "Collar of Subordination",
    CardType.UPGRADE,
    playValue = 3,
) {

    override fun playEffect(gameState: GameState) {
        // TODO
    }

    override fun playHoldDiscard(gameState: GameState): PlayChoice {
        return PlayChoice.DISCARD
    }

}
