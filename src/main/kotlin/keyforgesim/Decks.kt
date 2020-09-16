package keyforgesim

import keyforgesim.cards.Card
import keyforgesim.cards.VanillaCard
import keyforgesim.cards.aoa.logos.Eyegor
import keyforgesim.cards.aoa.untamed.PunctuatedEquilibrium
import keyforgesim.cards.aoa.dis.Yurk
import keyforgesim.cards.cota.logos.*
import keyforgesim.cards.mm.logos.EclecticInquiry
import keyforgesim.cards.wc.staralliance.InformationOfficerGray
import keyforgesim.cards.wc.staralliance.LayOfTheLand
import keyforgesim.cards.wc.logos.TautauVapors
import java.lang.RuntimeException

val labworks = buildStandardDecks("Labwork") { Labwork() }
val sloppyLabworks = buildStandardDecks("Sloppy Labwork") { SloppyLabwork() }
val yurks = buildStandardDecks("Yurk") { Yurk() }
val mothers = buildStandardDecks("Mother") { Mother() }
val infoOfficerGray = buildStandardDecks("Info Officer Gray") { InformationOfficerGray() }
val eyegor = buildStandardDecks("Eyegor") { Eyegor() }
val tautau = buildStandardDecks("Tautau Vapors") { TautauVapors() }
val eclecticInquiry = buildStandardDecks("Eclectic Inquiry") { EclecticInquiry() }

//val punctuatedEquilibrium = listOf<Deck>(
//    Deck(
//        "One Punctuated Equilibrium",
//        listOf<Card>()
//            .plus(List(1) { PunctuatedEquilibrium() })
//    ),
//
//    Deck(
//        "Two Punctuated Equilibrium",
//        listOf<Card>()
//            .plus(List(2) { PunctuatedEquilibrium() })
//    ),
//)
//
//val layOfTheLand = listOf<Deck>(
//    Deck(
//        "One Lay of the Land",
//        listOf<Card>()
//            .plus(List(1) { LayOfTheLand() })
//    ),
//
//    Deck(
//        "Two Lay of the Lands",
//        listOf<Card>()
//            .plus(List(2) { LayOfTheLand() })
//    ),
//)
//
//val timeTraveller = listOf<Deck>(
//    Deck(
//        "Time Traveller",
//        listOf<Card>()
//            .plus(List(1) { HelpFromFutureSelf() })
//            .plus(List(1) { TimeTraveller() })
//    ),
//
//    Deck(
//        "Time Traveller x 2",
//        listOf<Card>()
//            .plus(List(2) { HelpFromFutureSelf() })
//            .plus(List(2) { TimeTraveller() })
//    ),
//)
//
//val nathansseatLike = listOf<Deck>(
//    Deck(
//        "3 keyforgesim.getMothers",
//        listOf<Card>()
//            .plus(
//                List(3) { Mother() }
//            )
//    ),
//    Deck(
//        "1 sloppy",
//        listOf<Card>()
//            .plus(
//                List(1) { SloppyLabwork() }
//            )
//    ),
//    Deck(
//        "Time Traveller",
//        listOf<Card>()
//            .plus(List(1) { HelpFromFutureSelf() })
//            .plus(List(1) { TimeTraveller() })
//    ),
//    Deck(
//        "3 keyforgesim.getMothers + 1 sloppy + TT",
//        listOf<Card>()
//            .plus(List(1) { SloppyLabwork() })
//            .plus(List(3) { Mother() })
//            .plus(List(1) { TimeTraveller() })
//            .plus(List(1) { HelpFromFutureSelf() })
//
//    ),
//)
//
//val anteaterLike = listOf<Deck>(
//    Deck(
//        "3 keyforgesim.getMothers",
//        listOf<Card>()
//            .plus(
//                List(3) { Mother() }
//            )
//    ),
//    Deck(
//        "1 sloppy",
//        listOf<Card>()
//            .plus(
//                List(1) { SloppyLabwork() }
//            )
//    ),
//    Deck(
//        "Time Traveller",
//        listOf<Card>()
//            .plus(List(1) { HelpFromFutureSelf() })
//            .plus(List(1) { TimeTraveller() })
//    ),
//    Deck(
//        "3 keyforgesim.getMothers + 1 sloppy + TT",
//        listOf<Card>()
//            .plus(List(1) { SloppyLabwork() })
//            .plus(List(3) { Mother() })
//            .plus(List(1) { TimeTraveller() })
//            .plus(List(1) { HelpFromFutureSelf() })
//
//    ),
//)

fun buildStandardDecks(name: String, create: () -> Card): List<Deck> {
    return listOf(

        Deck(
            "One $name",
            listOf<Card>()
                .plus(
                    List(1) {
                        val card = create()
                        card.house = House.LOGOS
                        card
                    }
                )
        ),

        Deck(
            "Two $name",
            listOf<Card>()
                .plus(
                    List(2) {
                        val card = create()
                        card.house = House.LOGOS
                        card
                    }
                )
        ),

        Deck(
            "Four $name",
            listOf<Card>()
                .plus(
                    List(4) {
                        val card = create()
                        card.house = House.LOGOS
                        card
                    }
                )
        ),

        )
}

data class Deck(val name: String, val cards: List<Card>) {
    fun buildGame(): GameState {
        val cardHouses = cards.map { it.house }.toSet().toList()
        val houses = if (cardHouses.size > 3) {
            throw RuntimeException("Blargh, too many houses")
        } else if (cardHouses.size < 3) {
            cardHouses
                .plus(House.values().random())
                .plus(House.values().random())
                .plus(House.values().random())
                .subList(0, 3)
        } else {
            cardHouses
        }

        val houseOneToAdd = 12 - cards.count { it.house == houses[0] } - 1
        val houseTwoToAdd = 12 - cards.count { it.house == houses[1] } - 1
        val houseThreeToAdd = 12 - cards.count { it.house == houses[2] } - 1
        val library = cards
            .plus((0..houseOneToAdd).map { VanillaCard(houses[0]) })
            .plus((0..houseTwoToAdd).map { VanillaCard(houses[1]) })
            .plus((0..houseThreeToAdd).map { VanillaCard(houses[2]) })
            .shuffled()

        val gameState = GameState()
        gameState.library = library
        return gameState
    }
}
