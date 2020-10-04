package com.bestdamn.fortyk.crusade

import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bestdamn.fortyk.crusade.databinding.ActivityForceBinding
import com.bestdamn.fortyk.crusade.domain.Force
import com.bestdamn.fortyk.crusade.domain.Unit
import com.google.gson.Gson
import kotlin.time.Duration

class ForceAcitivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var force: Force

    override fun onResume() {
        super.onResume()

        val forceJson = intent.getStringExtra("force")
        force = Gson().fromJson(forceJson, Force::class.java)

        val binding: ActivityForceBinding = DataBindingUtil.setContentView(this, R.layout.activity_force)
        binding.force = force

        // listeners
        binding.btnSave.setOnClickListener {
            save(force)
        }

        binding.btnAddUnit.setOnClickListener {
            addUnit(force)
        }
        // setup recycler view
        viewManager = LinearLayoutManager(this)
        val adapterForces = force.units.sortedBy { it.name }.toTypedArray()
        viewAdapter = UnitAdapter(adapterForces)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerUnit).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        }
    }

    fun save(force: Force) {

        // name required to save
        if (force.name == null || force.name == "") {
            Toast.makeText(applicationContext, "Enter a Force Name to Save.", LENGTH_LONG).show()
            return
        }

        val prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val gson = Gson()

        val forceJson = gson.toJson(force)

        // store force id in list of forces
        val editor = prefs.edit()
        val forceSet = prefs.getStringSet("forceIds", mutableSetOf())
        forceSet?.add(force.id)

        editor.putStringSet("forceIds", forceSet)

        // store force in shared prefs
        editor.putString(force.id, forceJson)

        editor.apply()

        finish()

    }

    fun addUnit(force: Force) {
        val unitIntent = Intent(this, UnitActivity::class.java)
        val gson = Gson()
        val newUnitJson = gson.toJson(Unit(force_id = force.id))
        unitIntent.putExtra("unit", newUnitJson)
        startActivity(unitIntent)
    }

    override fun onBackPressed() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

}