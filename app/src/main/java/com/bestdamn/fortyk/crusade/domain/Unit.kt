package com.bestdamn.fortyk.crusade.domain

class Unit (
    // card
    val name: String,
    val role: String,
    val faction: String,
    val keywords: String,
    val type: String,
    val equipment: String,
    val psychicPowers: String,
    val warlordTraits: String,
    val relics: String,
    val upgradesAndAbilities: String,

    val powerRating: Int,
    val xp: Int,
    val crusadePoints: Int,

    // tallies
    val battlesPlayed: Int,
    val battlesSurvived: Int,

    // this battle
    val unitsDestroyed: Int,
    val unitsDestroyedPsychic: Int,
    val unitsDestroyedRanged: Int,
    val unitsDestroyedMelee: Int,
    val agenda1: Int,
    val agenda2: Int,
    val agenda3: Int,

    // in total
    val unitsDestroyedTotal: Int,
    val unitsDestroyedPsychicTotal: Int,
    val unitsDestroyedRangedTotal: Int,
    val unitsDestroyedMeleeTotal: Int,

    // rank
    val currentRank: Int, // 1(blooded), 2(battle-hardened), 3(heroic) or 4(legendary)
    val battleHonors: String,
    val battleScars: String
)
{

}