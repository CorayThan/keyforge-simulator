package keyforgesim.cards

import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.cards.oneOfCardsWithLeastOfHouse
import keyforgesim.debug

class BlankLogos : Card(
    "Blank Logos",
    CardType.ACTION,
    playValue = 2
) {
    init {
        house = House.LOGOS
    }
    override fun staticEffect(gameState: GameState) {
    }
}
class BlankDis : Card(
    "Blank Dis",
    CardType.ACTION,
    playValue = 2
) {

    init {
        house = House.DIS
    }
    override fun staticEffect(gameState: GameState) {
    }
}
class BlankShadows : Card(
    "Blank Shadows",
    CardType.ACTION,
    playValue = 2
) {
    init {
        house = House.SHADOWS
    }
    override fun staticEffect(gameState: GameState) {
    }
}