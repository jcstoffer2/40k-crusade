package com.bestdamn.fortyk.crusade.domain

import java.util.*

class Unit (
    // card
    val id: String = UUID.randomUUID().toString(),
    val name: String?,
    val role: String? = "",
    val faction: String? = "",
    val keywords: String? = "",
    val type: String? = "",
    val equipment: String? = "",
    val psychicPowers: String? = "",
    val warlordTraits: String? = "",
    val relics: String? = "",
    val upgradesAndAbilities: String? = "",

    val powerRating: Int? = 0,
    val xp: Int? = 0,
    val crusadePoints: Int? =0,

    // tallies
    val battlesPlayed: Int? = 0,
    val battlesSurvived: Int? = 0,

    // this battle
    val unitsDestroyed: Int? = 0,
    val unitsDestroyedPsychic: Int? = 0,
    val unitsDestroyedRanged: Int? = 0,
    val unitsDestroyedMelee: Int? = 0,
    val agenda1: Int? = 0,
    val agenda2: Int? = 0,
    val agenda3: Int? = 0,

    // in total
    val unitsDestroyedTotal: Int? = 0,
    val unitsDestroyedPsychicTotal: Int? = 0,
    val unitsDestroyedRangedTotal: Int? = 0,
    val unitsDestroyedMeleeTotal: Int? = 0,

    // rank
    val currentRank: Int? = 0, // 1(blooded), 2(battle-hardened), 3(heroic) or 4(legendary)
    val battleHonors: String? = "",
    val battleScars: String? = ""
)
{

}