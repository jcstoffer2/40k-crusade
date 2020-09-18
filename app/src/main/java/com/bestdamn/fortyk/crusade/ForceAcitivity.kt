package com.bestdamn.fortyk.crusade

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bestdamn.fortyk.crusade.domain.Force
import com.google.gson.Gson

class ForceAcitivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_force)

        val forceJson = intent.getStringExtra("force")
        val force = Gson().fromJson(forceJson, Force::class.java)

        Log.d("FORCECHECK", force.name)

        // TODO: replace these with 2 way binding: https://developer.android.com/topic/libraries/data-binding/two-way
        findViewById<EditText>(R.id.forceName).setText(force.name)
        findViewById<EditText>(R.id.forceFaction).setText(force.faction)
        findViewById<EditText>(R.id.playerName).setText(force.playerName)
        findViewById<EditText>(R.id.tally).setText(force.tally.toString())
        findViewById<EditText>(R.id.battlesWon).setText(force.battlesWon.toString())
        findViewById<EditText>(R.id.requisitionPoints).setText(force.requisitionPoints.toString())
        findViewById<EditText>(R.id.supplyLimit).setText(force.supplyLimit.toString())
        findViewById<EditText>(R.id.supplyUsed).setText(force.supplyUsed.toString())
        findViewById<EditText>(R.id.goalsInfoAndVictories).setText(force.goalsInfoAndVictories)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            save(force)
        }

    }

    fun save(force: Force) {
        Log.d("FORCECHECK", force.name)
        val prefs = getPreferences(MODE_PRIVATE)
        val gson = Gson()

        val forceJson = gson.toJson(force)

        val editor = prefs.edit()
        val nameSet = prefs.getStringSet("forceNames", mutableSetOf())
        nameSet?.add(force.name)
        editor.putStringSet("forceNames", nameSet)
        editor.putString(force.name, forceJson)

        // TODO: commit new / updated force

    }
}