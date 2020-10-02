package com.bestdamn.fortyk.crusade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bestdamn.fortyk.crusade.domain.Force
import com.bestdamn.fortyk.crusade.domain.Unit
import com.google.gson.Gson
import java.util.*

/**
 * TODO: ADDITIONAL UI UPDATES (Icons)
 * TODO: AD BANNER ON BOTTOM OF SCREENS
 * TODO: ADDITIONAL UI UPDATES (Themes)
 */

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onResume() {
        super.onResume()

//        //        // TODO: remove this clearing on app boot.
//        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE).edit().clear()
//        prefs.apply()
//
//        bootstrap() // TODO: stop calling this

        setContentView(R.layout.activity_main)
        val existingForces = getExistingForces()
        val adapterForces = existingForces.toTypedArray()

        // setup recycler view
        viewManager = LinearLayoutManager(this)
        viewAdapter = ForceAdapter(adapterForces)
        recyclerView = findViewById<RecyclerView>(R.id.forceRecycler).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    /**
     * Read the existing forces stored in shared prefs.
     */
    private fun getExistingForces(): List<Force> {
        val existingForces = mutableListOf<Force>()
        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val gson = Gson()
        val nameSet = prefs.getStringSet("forceIds", null)
        nameSet?.forEach {
            val storedForceJson = prefs.getString(it, "")
            val force = gson.fromJson(storedForceJson, Force::class.java)
            existingForces.add(force)
        }
        return existingForces
    }

    fun addForce(view: View) {
        val forceIntent = Intent(this, ForceAcitivity::class.java)
        val gson = Gson()
        val newForceJson = gson.toJson(Force(units = mutableSetOf()))
        forceIntent.putExtra("force", newForceJson)

        startActivity(forceIntent)
    }

    private fun bootstrap() {
        // test units and forces
        // TODO: actual units

        val force1 = Force(
            name = "The BlightBorne",
            faction = "Death Guard",
            playerName = "test",
            tally = 1,
            battlesWon = 1,
            requisitionPoints = 1,
            supplyLimit = 5,
            supplyUsed = 5,
            goalsInfoAndVictories = null,

            units = mutableSetOf()
        )

        val unit = Unit(
            name = "testUnit",
            force_id = force1.id
        )
        force1.units.add(unit)


        val force2 = Force(
            name = "Baal's Angels",
            faction = "Blood Angels",
            playerName = "test",
            tally = 1,
            battlesWon = 1,
            requisitionPoints = 1,
            supplyLimit = 5,
            supplyUsed = 5,
            goalsInfoAndVictories = null,

            units = mutableSetOf()
        )
        val unit2 = Unit(
            name = "testUnit2",
            force_id = force2.id
        )
        force2.units.add(unit2)

        val force3 = Force(
            name = "Soldiers of Silence",
            faction = "Nercons",
            units = mutableSetOf()
        )

        val unit3 = Unit(
            name = "testUnit3",
            force_id = force3.id
        )

        force3.units.add(unit3)

        // setup prefs, editor, and gson.
        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()

        // wipe any existing data****
        editor.clear()

        // store the list of forces (force pref names)
        editor.putStringSet("forceIds", mutableSetOf(force1.id, force2.id, force3.id))

        // write to shared prefs
        val force1Json = gson.toJson(force1)
        editor.putString(force1.id, force1Json)
        editor.apply()

        val force2Json = gson.toJson(force2)
        editor.putString(force2.id, force2Json)
        editor.apply()

        val force3Json = gson.toJson(force3)
        editor.putString(force3.id, force3Json)
        editor.apply()

        val retrievedForcesList = mutableListOf<Force>()

        // read the set of force names
        val forceIds = prefs.getStringSet("forceIds", null)

        // loop through those names and read the json stored in shared prefs
        // for each
        forceIds?.forEach {
            val storedForceJson = prefs.getString(it, "")
            val force = gson.fromJson(storedForceJson, Force::class.java)
            retrievedForcesList.add(force)
        }

        for (force in retrievedForcesList) {
            Log.d("BOOTSTRAP-FORCE", force.name.toString())
            Log.d("BOOTSTRAP-FORCE", force.id)
            Log.d("BOOTSTRAP-FORCE", force.units.elementAt(0).name.toString())
        }
    }
}