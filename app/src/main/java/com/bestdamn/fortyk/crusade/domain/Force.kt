package com.bestdamn.fortyk.crusade.domain

import android.preference.PreferenceManager

// TODO: 2 way binding on Int fields. Maybe set textfield inputType?
class Force (
    var name: String,
    var faction: String? = "",
    var playerName: String? = "",
    var tally: Int? = 0,
    var battlesWon: Int? = 0,
    var requisitionPoints: Int? = 0,
    var supplyLimit: Int? = 0,
    var supplyUsed: Int? = 0,
    var goalsInfoAndVictories: String? = "",

    var units: MutableList<Unit> // TODO: probably make this nullable
)