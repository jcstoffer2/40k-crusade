package com.bestdamn.fortyk.crusade

import android.content.Intent
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bestdamn.fortyk.crusade.databinding.ActivityUnitBinding
import com.bestdamn.fortyk.crusade.domain.Force
import com.bestdamn.fortyk.crusade.domain.Unit
import com.google.gson.Gson

class UnitActivity : AppCompatActivity() {

    private lateinit var unit: Unit
    override fun onResume() {

        super.onResume()
        val unitJson = intent.getStringExtra("unit")
        unit = Gson().fromJson(unitJson, Unit::class.java)
        val binding: ActivityUnitBinding = DataBindingUtil.setContentView(this, R.layout.activity_unit)
        binding.unit = unit

        // check the currentRank and select the correct checkbox.
        when (unit.currentRank) {
            "Blooded" -> findViewById<RadioButton>(R.id.rdBtnBlooded).isChecked = true
            "Hardened" -> findViewById<RadioButton>(R.id.rdBtnBattleHardened).isChecked = true
            "Heroic" -> findViewById<RadioButton>(R.id.rdBtnHeroic).isChecked = true
            "Legendary" -> findViewById<RadioButton>(R.id.rdBtnLegendary).isChecked = true
        }

        binding.btnSave.setOnClickListener {
            saveUnit(unit)
        }

        binding.btnBack.setOnClickListener {
            goBack(unit)
        }
    }

    private fun saveUnit(unit: Unit) {

        // name required to save
        if (unit.name == null || unit.name == "") {
            Toast.makeText(applicationContext, "Enter a Unit Name to Save.", Toast.LENGTH_LONG).show()
            return
        }

        // check the radio buttons and set the correct field
        val radioGroup: RadioGroup = findViewById<RadioGroup>(R.id.rdGrpRank)
        val checkedRadioButton: RadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        unit.currentRank = checkedRadioButton.text.toString()

        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val gson = Gson()

        // get the force
        val forceJson = prefs.getString(unit.force_id, "")
        val force: Force = gson.fromJson(forceJson, Force::class.java)

        // update unit list TODO: gotta be a better way to do this.
        val foundUnit = force.units.find { it.id == unit.id }
        if (foundUnit == null) {
            force.units.add(unit)
        } else {
            force.units.remove(foundUnit) // remove old
            force.units.add(unit) // add new
        }

        // save the force
        val updatedForceJson = gson.toJson(force)
        val editor = prefs.edit()
        editor.putString(force.id, updatedForceJson)
        editor.apply()

    }

    override fun onBackPressed() {
        goBack(unit)
    }

    private fun goBack(unit: Unit) {
        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val forceJson = prefs.getString(unit.force_id, "")
        val forceIntent = Intent(this, ForceAcitivity::class.java)
        forceIntent.putExtra("force", forceJson)
        startActivity(forceIntent)
    }


}
