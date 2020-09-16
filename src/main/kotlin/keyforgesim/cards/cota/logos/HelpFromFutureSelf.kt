package keyforgesim.cards.cota.logos

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class HelpFromFutureSelf : Card(
    "Help from Future Self",
    type = CardType.ACTION,
    playValue = 3,
    amber = 1
) {

    override fun playEffect(gameState: GameState) {
        var ttFound =
            gameState.library.find { it::class.java == TimeTraveller::class.java && (it as TimeTraveller).playedOnTurn != gameState.turns }
        if (ttFound == null) {
            ttFound =
                gameState.discard.find { it::class.java == TimeTraveller::class.java && (it as TimeTraveller).playedOnTurn != gameState.turns }
        }
        gameState.moveToHand(ttFound)
        gameState.shuffleDiscardIntoDeck()
    }

}