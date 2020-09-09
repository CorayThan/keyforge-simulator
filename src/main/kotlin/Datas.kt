val labworks = buildStandardDecks("Labwork") { Labwork() }
val sloppyLabworks = buildStandardDecks("Sloppy Labwork") { SloppyLabwork() }
val yurks = buildStandardDecks("Yurk") { Yurk() }
val drawPips = buildStandardDecks("Draw Pips") { DrawPip() }
val mothers = buildStandardDecks("Mother") { Mother(House.ONE) }
val infoOfficerGray = buildStandardDecks("Info Officer Gray") { InfoOfficerGray() }
val eyegor = buildStandardDecks("Eyegor") { Eyegor(House.ONE) }
val tautau = buildStandardDecks("Tautau Vapors") { TautauVapors(House.ONE) }
val eclecticInquiry = buildStandardDecks("Eclectic Inquiry") { EclecticInquiry(House.ONE) }

val punctuatedEquilibrium = listOf<Deck>(
    Deck(
        "One Punctuated Equilibrium",
        listOf<Card>()
            .plus(List(1) { PunctuatedEquilibrium(House.ONE) })
    ),

    Deck(
        "Two Punctuated Equilibrium",
        listOf<Card>()
            .plus(List(2) { PunctuatedEquilibrium(House.ONE) })
    ),
)

val layOfTheLand = listOf<Deck>(
    Deck(
        "One Lay of the Land",
        listOf<Card>()
            .plus(List(1) { LayOfTheLand(House.ONE) })
    ),

    Deck(
        "Two Lay of the Lands",
        listOf<Card>()
            .plus(List(2) { LayOfTheLand(House.ONE) })
    ),
)

val timeTraveller = listOf<Deck>(
    Deck(
        "Time Traveller",
        listOf<Card>()
            .plus(List(1) { HelpFromFutureSelf(House.ONE) })
            .plus(List(1) { TimeTraveller(House.ONE) })
    ),

    Deck(
        "Time Traveller x 2",
        listOf<Card>()
            .plus(List(2) { HelpFromFutureSelf(House.ONE) })
            .plus(List(2) { TimeTraveller(House.ONE) })
    ),
)

val nathansseatLike = listOf<Deck>(
    Deck(
        "3 mothers",
        listOf<Card>()
            .plus(
                List(3) { Mother(House.ONE) }
            )
    ),
    Deck(
        "1 sloppy",
        listOf<Card>()
            .plus(
                List(1) { SloppyLabwork() }
            )
    ),
    Deck(
        "Time Traveller",
        listOf<Card>()
            .plus(List(1) { HelpFromFutureSelf(House.ONE) })
            .plus(List(1) { TimeTraveller(House.ONE) })
    ),
    Deck(
        "Draw Pip",
        listOf<Card>()
            .plus(List(1) { DrawPip(House.ONE) })
    ),
    Deck(
        "3 mothers + 1 sloppy + TT",
        listOf<Card>()
            .plus(List(1) { SloppyLabwork(House.ONE) })
            .plus(List(3) { Mother(House.ONE) })
            .plus(List(1) { TimeTraveller(House.ONE) })
            .plus(List(1) { HelpFromFutureSelf(House.ONE) })
            .plus(List(1) { DrawPip(House.ONE) })

    ),
)

val anteaterLike = listOf<Deck>(
    Deck(
        "3 mothers",
        listOf<Card>()
            .plus(
                List(3) { Mother(House.ONE) }
            )
    ),
    Deck(
        "1 sloppy",
        listOf<Card>()
            .plus(
                List(1) { SloppyLabwork() }
            )
    ),
    Deck(
        "Time Traveller",
        listOf<Card>()
            .plus(List(1) { HelpFromFutureSelf(House.ONE) })
            .plus(List(1) { TimeTraveller(House.ONE) })
    ),
    Deck(
        "Draw Pip",
        listOf<Card>()
            .plus(List(1) { DrawPip(House.ONE) })
    ),
    Deck(
        "3 mothers + 1 sloppy + TT",
        listOf<Card>()
            .plus(List(1) { SloppyLabwork(House.ONE) })
            .plus(List(3) { Mother(House.ONE) })
            .plus(List(1) { TimeTraveller(House.ONE) })
            .plus(List(1) { HelpFromFutureSelf(House.ONE) })
            .plus(List(1) { DrawPip(House.ONE) })

    ),
)

fun buildStandardDecks(name: String, create: () -> Card): List<Deck> {
    return listOf(

        Deck(
            "One $name",
            listOf<Card>()
                .plus(
                    List(1) { create() }
                )
        ),

        Deck(
            "Two $name",
            listOf<Card>()
                .plus(
                    List(2) { create() }
                )
        ),

        Deck(
            "Four $name",
            listOf<Card>()
                .plus(
                    List(4) { create() }
                )
        ),

        )
}