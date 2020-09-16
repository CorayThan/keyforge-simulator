package keyforgesim.cards.aoa.shadows

import keyforgesim.*
import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class SkeletonKey : Card(
    "Skeleton Key",
    useValue = 1,
    type = CardType.ARTIFACT,
    action = {
        val randomCreature = it.findCardsOnBoard(CardType.CREATURE).randomOrNull()
        if (randomCreature != null) {
            odds(75) {
                it.attemptToCapture(randomCreature, 1)
            }
        }
    }
) {

}
