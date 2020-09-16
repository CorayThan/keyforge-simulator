package keyforgesim.cards.wc.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder

class TautauVapors : Card(
    "Tautau Vapors",
    type = CardType.ACTION,
    playValue = 3,
    draw = 2,
    playOrder = PlayOrder.FIRST
) {

    override fun playEffect(gameState: GameState) {
        gameState.archiveCardFromHand(house)
    }
}
