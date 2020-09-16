package keyforgesim.cards.cota.logos

import keyforgesim.FutureEffect
import keyforgesim.GameState
import keyforgesim.House
import keyforgesim.cards.Card
import keyforgesim.cards.CardType
import keyforgesim.coinFlip

class LibraryOfBabble : Card(
    "Library of Babble",
    useValue = 1,
    type = CardType.ARTIFACT,
    action = {
        it.drawCards(1)
    }
) {

}
