package keyforgesim.cards.cota.dis

import keyforgesim.cards.Card
import keyforgesim.cards.CardType

class LashOfBrokenDreams : Card(
    "Lash of Broken Dreams",
    type = CardType.ARTIFACT,
    useValue = 1,
    action = { gameState ->
        gameState.currentTurnRecord.opponentKeyCost += 3
    },
) {


}
