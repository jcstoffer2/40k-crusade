package com.bestdamn.fortyk.crusade.domain

import java.util.*

class Unit (
    // card
    val id: String = UUID.randomUUID().toString(),
    val force_id: String,
    var name: String? = "",
    var role: String? = "",
    var faction: String? = "",
    var keywords: String? = "",
    var type: String? = "",
    var equipment: String? = "",
    var psychicPowers: String? = "",
    var warlordTraits: String? = "",
    var relics: String? = "",
    var upgradesAndAbilities: String? = "",

    var powerRating: Int? = 0,
    var xp: Int? = 0,
    var crusadePoints: Int? =0,

    // tallies
    var battlesPlayed: Int? = 0,
    var battlesSurvived: Int? = 0,

    // this battle
    var unitsDestroyed: Int? = 0,
    var unitsDestroyedPsychic: Int? = 0,
    var unitsDestroyedRanged: Int? = 0,
    var unitsDestroyedMelee: Int? = 0,
    var agenda1: Int? = 0,
    var agenda2: Int? = 0,
    var agenda3: Int? = 0,

    // in total
    var unitsDestroyedTotal: Int? = 0,
    var unitsDestroyedPsychicTotal: Int? = 0,
    var unitsDestroyedRangedTotal: Int? = 0,
    var unitsDestroyedMeleeTotal: Int? = 0,

    // rank
    var currentRank: String? = "Blooded", // Blooded, Hardened, Heroic, Legendary
    var battleHonors: String? = "",
    var battleScars: String? = ""
)
{

}