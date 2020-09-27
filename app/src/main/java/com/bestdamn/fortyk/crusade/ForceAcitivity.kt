package com.bestdamn.fortyk.crusade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bestdamn.fortyk.crusade.databinding.ActivityForceBinding
import com.bestdamn.fortyk.crusade.domain.Force
import com.google.gson.Gson

class ForceAcitivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forceJson = intent.getStringExtra("force")
        val force = Gson().fromJson(forceJson, Force::class.java)

        val binding: ActivityForceBinding = DataBindingUtil.setContentView(this, R.layout.activity_force)
        binding.force = force

        // listeners
        binding.btnSave.setOnClickListener {
            save(force)
        }

        binding.btnAddUnit.setOnClickListener {
            addUnit(force)
        }
    }

    fun save(force: Force) {
        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val gson = Gson()

        val forceJson = gson.toJson(force)

        val editor = prefs.edit()
        val forceSet = prefs.getStringSet("forceIds", mutableSetOf())
        Log.d("NAMESET", forceSet.toString())
        Log.d("FORCEJSON", forceJson.toString())
        forceSet?.add(force.id)
        editor.putStringSet("forceIds", forceSet)

        editor.putString(force.id, forceJson)

        editor.apply()

    }

    fun addUnit(force: Force) {
        val unitIntent = Intent(this, UnitActivity::class.java)
        val gson = Gson()
        val forceJson = gson.toJson(force)
        unitIntent.putExtra("force", forceJson)

        startActivity(unitIntent)
    }

}