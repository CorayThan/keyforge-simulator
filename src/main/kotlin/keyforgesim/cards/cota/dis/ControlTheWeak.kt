package keyforgesim.cards.cota.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class ControlTheWeak : Card(
    "Control the Weak",
    type = CardType.ACTION,
    playValue = 4,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
    }

}
