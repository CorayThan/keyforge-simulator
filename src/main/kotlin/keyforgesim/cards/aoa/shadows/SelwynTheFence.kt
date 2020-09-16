package keyforgesim.cards.aoa.shadows

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class SelwynTheFence : Card(
    "Selwyn the Fence",
    CardType.CREATURE,
    power = 3,
    useValue = 1
) {

    override fun reap(gameState: GameState) {
        super.playEffect(gameState)
        fightReap(gameState)
    }

    override fun fight(gameState: GameState) {
        super.playEffect(gameState)
        fightReap(gameState)
    }

    private fun fightReap(gameState: GameState) {
        val hasCaptured = gameState.board.find { it.capturedAmber > 0 }
        if (hasCaptured != null) {
            hasCaptured.capturedAmber--
            gameState.modifyAmber(1)
        }
    }
}
