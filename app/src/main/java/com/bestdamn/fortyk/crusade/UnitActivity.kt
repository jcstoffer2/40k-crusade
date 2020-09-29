package com.bestdamn.fortyk.crusade

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bestdamn.fortyk.crusade.databinding.ActivityUnitBinding
import com.bestdamn.fortyk.crusade.domain.Force
import com.bestdamn.fortyk.crusade.domain.Unit
import com.google.gson.Gson

class UnitActivity : AppCompatActivity() {

    private val UNIT_ADDED = 1

    override fun onResume() {
        super.onResume()
        val unitJson = intent.getStringExtra("unit")
        val unit = Gson().fromJson(unitJson, Unit::class.java)
        Log.d("UNITNAME::",  unit.name.toString())
        val binding: ActivityUnitBinding = DataBindingUtil.setContentView(this, R.layout.activity_unit)
        binding.unit = unit

        binding.btnSave.setOnClickListener {
            saveUnit(unit)
        }
    }

    private fun saveUnit(unit: Unit) {
        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val gson = Gson()
        Log.d("UNIT NAME::", unit.name.toString())
        Log.d("UNIT TYPE::", unit.type.toString())
        // get the force
        val forceJson = prefs.getString(unit.force_id,"")
        val force: Force = gson.fromJson(forceJson, Force::class.java)
        // update unit list TODO: gotta be a better way to do this.
        val foundUnit = force.units.find { it.id == unit.id }
        if(foundUnit == null) {
            force.units.add(unit)
        } else {
            force.units.remove(foundUnit) // remove old
            force.units.add(unit) // add new
        }

        // save the force
        val updatedForceJson = gson.toJson(force)
        val editor = prefs.edit()
        editor.putString(force.id, updatedForceJson)
        editor.commit()

        // send force with new unit back to force activity
        val forceIntent = Intent(this, ForceAcitivity::class.java)
        val newForceJson = gson.toJson(force)
        forceIntent.putExtra("force", newForceJson)

        startActivity(forceIntent)
    }
}