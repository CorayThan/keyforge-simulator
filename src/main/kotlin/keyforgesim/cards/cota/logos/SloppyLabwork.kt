package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.*
import keyforgesim.debug
import keyforgesim.printDebug

class SloppyLabwork : Card(
    "Sloppy Labwork",
    type = CardType.ACTION,
    playValue = 2,
    amber = 1,
    playOrder = PlayOrder.LAST,
) {

    override fun playEffect(gameState: GameState) {

        var optionsToDiscard = gameState.hand.filter { it.house != house }
        if (optionsToDiscard.size > 1) {
            if (optionsToDiscard.any { it::class.java == VanillaCard::class.java }) {
                optionsToDiscard = optionsToDiscard.filter { it::class.java == VanillaCard::class.java }
            }
            val discard = optionsToDiscard.oneOfCardsWithLeastOfHouse()

            printDebug {
                "Discard card ${discard.toString()} from hand: ${gameState.handString()}"
            }

            if (discard != null) {
                gameState.discardCards(listOf(discard))
            }
        }

        gameState.archiveCardFromHand()
    }

}