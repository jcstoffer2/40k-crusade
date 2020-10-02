package com.bestdamn.fortyk.crusade

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bestdamn.fortyk.crusade.domain.Force
import com.bestdamn.fortyk.crusade.domain.Unit
import com.google.gson.Gson
import kotlinx.android.synthetic.main.unit_list_text_view.view.*

class UnitAdapter(private val myDataset: Array<Unit>) :
    RecyclerView.Adapter<UnitAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val unitLayout: LinearLayout) : RecyclerView.ViewHolder(unitLayout)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UnitAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.unit_list_text_view, parent, false) as LinearLayout
        // TODO: set the view's size, margins, paddings and layout parameters
        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.unitLayout.textViewUnitName.text = myDataset[position].name
        holder.unitLayout.textViewUnitType.text = myDataset[position].type

        holder.itemView.setOnClickListener(View.OnClickListener {
            val gson = Gson()
            val context = it.context
            val unitJson = gson.toJson(myDataset[position])
            val unitIntent = Intent(context, UnitActivity::class.java)
            unitIntent.putExtra("unit", unitJson)
            context.startActivity(unitIntent)
        })

        holder.itemView.setOnLongClickListener(
            View.OnLongClickListener {
                val builder = AlertDialog.Builder(it.context).apply {
                    setTitle("Delete Unit?")
                    setMessage("Are you sure you want to delete the Unit: " + myDataset[position].name + "?")
                    setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                        val sharedPrefs = it.context.getSharedPreferences(
                            "sharedPrefs",
                            Context.MODE_PRIVATE
                        )
                        val editor = sharedPrefs.edit()
                        val forceJson = sharedPrefs.getString(myDataset[position].force_id, null)
                        val gson = Gson()
                        val force = gson.fromJson(forceJson, Force::class.java)
                        val unitToRemove = force.units.find { unit -> unit.id == myDataset[position].id }
                        force.units.remove(unitToRemove)
                        val updatedForceJson = gson.toJson(force)
                        editor.putString(force.id, updatedForceJson)
                        editor.apply()

                        // restart the force activity to pull new unit list
                        val intent = Intent(context, ForceAcitivity::class.java)
                        intent.putExtra("force", updatedForceJson)
                        context.startActivity(intent)
                    })
                    setNegativeButton("No", null)

                }.show()
                true
            }
        )

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}