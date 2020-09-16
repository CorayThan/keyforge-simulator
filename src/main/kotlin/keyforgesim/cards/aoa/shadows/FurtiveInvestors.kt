package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayChoice
import keyforgesim.cards.PlayOrder
import keyforgesim.coinFlip
import keyforgesim.odds

class FurtiveInvestors : Card(
    "Furtive Investors",
    type = CardType.ACTION,
    playValue = 1,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        odds(15) {
            gameState.modifyAmber(1)
        }
    }

}
