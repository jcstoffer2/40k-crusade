package com.bestdamn.fortyk.crusade.domain

import android.preference.PreferenceManager


class Force (
    val name: String,
    val faction: String? = "",
    val playerName: String? = "",
    val tally: Int? = 0,
    val battlesWon: Int? = 0,
    val requisitionPoints: Int? = 0,
    val supplyLimit: Int? = 0,
    val supplyUsed: Int? = 0,
    val goalsInfoAndVictories: String? = "",

    val units: MutableList<Unit> // TODO: probably make this nullable
)