package keyforgesim.cards.wc.staralliance

import keyforgesim.FutureEffect
import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.debug


class InformationOfficerGray : Card(
    "Information Officer Gray",
    playValue = 1,
    useValue = 1,
    type = CardType.CREATURE,
) {

    override fun playEffect(gameState: GameState) {
        grayArchive(gameState)
    }

    override fun reap(gameState: GameState) {
        super.reap(gameState)
        grayArchive(gameState)
    }

    private fun grayArchive(gameState: GameState) {
        val options = gameState.hand.filter { it.house != House.STAR }
        if (options.isNotEmpty()) {
            gameState.archiveCardFromHand(options)
        }
    }
}
