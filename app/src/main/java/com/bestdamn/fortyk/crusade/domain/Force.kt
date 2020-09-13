package com.bestdamn.fortyk.crusade.domain

class Force(
    val name: String,
    val faction: String,
    val playerName: String,
    val tally: Int,
    val battlesWon: Int,
    val requisitionPoints: Int,
    val supplyLimit: Int,
    val supplyUsed: Int,
    val goalsInfoAndVictories: String,

    val units: MutableList<Unit>
) {

}