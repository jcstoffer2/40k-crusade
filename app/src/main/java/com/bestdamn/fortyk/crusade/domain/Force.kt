package com.bestdamn.fortyk.crusade.domain

import java.util.*

data class Force(
    val id: String = UUID.randomUUID().toString(),
    var name: String? = "",
    var faction: String? = "",
    var playerName: String? = "",
    var tally: Int? = 0,
    var battlesWon: Int? = 0,
    var requisitionPoints: Int? = 0,
    var supplyLimit: Int? = 0,
    var supplyUsed: Int? = 0,
    var goalsInfoAndVictories: String? = "",

    var units: MutableSet<Unit>
)