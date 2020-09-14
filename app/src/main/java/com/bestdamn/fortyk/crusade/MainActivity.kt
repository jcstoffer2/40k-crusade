package com.bestdamn.fortyk.crusade

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bestdamn.fortyk.crusade.domain.Force
import com.bestdamn.fortyk.crusade.domain.Unit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addForce(view: View) {

        // TODO: convert to real add  method.
        // test units and forces
        val unit = Unit(
            name = "testUnit"
        )

        val force1 = Force(
            name = "force1",
            faction = "test",
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
            name = "testUnit"
        )

        val force2 = Force(
            name = "test",
            faction = "test",
            playerName = "test",
            tally = 1,
            battlesWon = 1,
            requisitionPoints = 1,
            supplyLimit = 5,
            supplyUsed = 5,
            goalsInfoAndVictories = null,

            units = mutableListOf(unit, unit2)
        )

        // setup prefs, editor, and gson.
        val prefs = getPreferences(MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()

        // store the list of forces (force pref names)
        editor.putStringSet("forceNames", mutableSetOf(force1.name, force2.name))

        // write to shared prefs
        val force1Json = gson.toJson(force1)
        editor.putString(force1.name, force1Json)
        editor.commit()

        val force2Json = gson.toJson(force2)
        editor.putString(force2.name, force2Json)
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
           Log.d("THEFORCE", force.name)
        }

    }
}