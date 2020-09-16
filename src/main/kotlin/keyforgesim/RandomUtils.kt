package keyforgesim

import kotlin.random.Random

fun coinFlip(perform: (() -> Unit)? = null) = Random.nextBoolean()
fun twentyFivePercentOdds(perform: (() -> Unit)? = null) = Random.nextBoolean() && Random.nextBoolean()
fun seventyFivePercentOdds(perform: (() -> Unit)? = null) = Random.nextBoolean() || Random.nextBoolean()

fun oneToOneHundred() = Random.nextInt(100)

fun odds(percent: Int, perform: (() -> Unit)? = null) = percent > oneToOneHundred()
