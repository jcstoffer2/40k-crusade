package com.bestdamn.fortyk.crusade

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.recyclerview.widget.RecyclerView
import com.bestdamn.fortyk.crusade.domain.Force
import com.google.gson.Gson
import kotlinx.android.synthetic.main.force_list_text_view.view.*

class ForceAdapter(private val myDataset: Array<Force>) :
    RecyclerView.Adapter<ForceAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val forceLayout: LinearLayout) : RecyclerView.ViewHolder(forceLayout)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForceAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.force_list_text_view, parent, false) as LinearLayout
        // TODO: set the view's size, margins, paddings and layout parameters

        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.forceLayout.textViewForceName.text = myDataset[position].name
        holder.forceLayout.textViewForceFaction.text = myDataset[position].faction

        holder.itemView.setOnClickListener(View.OnClickListener {
            val gson = Gson()
            val context = it.context
            val forceJson = gson.toJson(myDataset[position])
            val forceIntent = Intent(context, ForceAcitivity::class.java)
            forceIntent.putExtra("force", forceJson)
            context.startActivity(forceIntent)
        })

        holder.itemView.setOnLongClickListener(
            View.OnLongClickListener {
                val builder = AlertDialog.Builder(it.context).apply {
                    setTitle("Delete Force?")
                    setMessage("Are you sure you want to delete the Force: " + myDataset[position].name + "?")
                    setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(it.context, "TODO: DELETE STUFFS MAN", LENGTH_LONG).show()
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