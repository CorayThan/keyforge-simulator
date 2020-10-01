package keyforgesim.cards.aoa.dis

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.PlayOrder
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug
import keyforgesim.printDebug

class Yurk : Card(
    "Yurk",
    CardType.CREATURE,
    useValue = 1,
    playOrder = PlayOrder.LAST,
    power = 4,
) {

    override fun playEffect(gameState: GameState) {

        val optionsToDiscard = gameState.hand.filter { it.house != house }
        if (optionsToDiscard.isNotEmpty()) {
            val discard = optionsToDiscard.oneOfCardsWithLeastOfHouse()

            printDebug {
                "Discard card ${discard.toString()} from hand: ${gameState.handString()}"
            }

            if (discard != null) {
                gameState.discardCards(listOf(discard))
            }
        }
    }

}