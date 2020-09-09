val labworks = listOf(
    Deck(
        "Two Labwork",
        listOf<Card>()
            .plus(
                List(2) { Labwork() }
            )
    ),

    Deck(
        "Four Labwork",
        listOf<Card>()
            .plus(
                List(4) { Labwork() }
            )
    ),

    )

val sloppyLabworks = listOf(

    Deck(
        "One Sloppy Labwork",
        listOf<Card>()
            .plus(
                List(1) { SloppyLabwork() }
            )
    ),

    Deck(
        "Two Sloppy Labwork",
        listOf<Card>()
            .plus(
                List(2) { SloppyLabwork() }
            )
    ),

    Deck(
        "Four Sloppy Labwork",
        listOf<Card>()
            .plus(
                List(4) { SloppyLabwork() }
            )
    ),

    Deck(
        "eight Sloppy Labwork",
        listOf<Card>()
            .plus(
                List(8) { SloppyLabwork() }
            )
    ),
)

val yurks = listOf(

    Deck(
        "One Yurk",
        listOf<Card>()
            .plus(
                List(1) { Yurk() }
            )
    ),

    Deck(
        "Two Yurk",
        listOf<Card>()
            .plus(
                List(2) { Yurk() }
            )
    ),

    Deck(
        "Four Yurk",
        listOf<Card>()
            .plus(
                List(4) { Yurk() }
            )
    ),

)