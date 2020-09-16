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


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bootstrap()

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

    // TODO: stop calling this
    private fun bootstrap() {
        // test units and forces
        // TODO: actual units
        val unit = Unit(
            name = "testUnit"
        )

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

            units = mutableListOf(unit)
        )

        val unit2 = Unit(
            name = "testUnit2"
        )

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

            units = mutableListOf(unit2)
        )

        val unit3 = Unit(
            name = "testUnit3"
        )

        val force3 = Force(
            name = "Soldiers of Silence",
            faction = "Nercons",
            units = mutableListOf(unit3)
        )

        // setup prefs, editor, and gson.
        val prefs = getPreferences(MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()

        // wipe any existing data****
        editor.clear()

        // store the list of forces (force pref names)
        editor.putStringSet("forceNames", mutableSetOf(force1.name, force2.name, force3.name))

        // write to shared prefs
        val force1Json = gson.toJson(force1)
        editor.putString(force1.name, force1Json)
        editor.commit()

        val force2Json = gson.toJson(force2)
        editor.putString(force2.name, force2Json)
        editor.commit()

        val force3Json = gson.toJson(force3)
        editor.putString(force3.name, force3Json)
        editor.commit()

        val retrievedForcesList = mutableListOf<Force>()

        // read the set of force names
        val forceNameSet = prefs.getStringSet("forceNames", null)

        // loop through those names and read the json stored in shared prefs
        // for each
        forceNameSet?.forEach {
            val storedForceJson = prefs.getString(it, "")
            val force = gson.fromJson(storedForceJson, Force::class.java)
            retrievedForcesList.add(force)
        }

        for (force in retrievedForcesList) {
            Log.d("BOOTSTRAP", force.name)
            Log.d("BOOTSTRAP", force.units[0].name)
        }
    }

    /**
     * Read the existing forces stored in shared prefs.
     */
    private fun getExistingForces() : List<Force> {
        val existingForces = mutableListOf<Force>()
        val prefs = getPreferences(MODE_PRIVATE)
        val gson = Gson()
        val nameSet = prefs.getStringSet("forceNames", null)
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
        val newForceJson = gson.toJson(Force(name = "Enter Force Name", units = mutableListOf()))
        forceIntent.putExtra("force", newForceJson)

        startActivity(forceIntent)
    }
}