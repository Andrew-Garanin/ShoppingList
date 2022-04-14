package com.example.shoppinglist.measuringunitslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.database.MeasuringUnit

class MeasuringUnitsListAdapter(var viewModel: MeasuringUnitsListViewModel): RecyclerView.Adapter<MeasuringUnitsListViewHolder>(){
    var data = listOf<MeasuringUnit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MeasuringUnitsListViewHolder, position: Int) {
        val item = data[position]
        holder.text.text = item.name

        holder.deleteButton.setOnClickListener {
            viewModel.onDeleteMeasuringUnit(item.id)
            Toast.makeText(viewModel.getApplication(), it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
        }

        holder.card.setOnClickListener{
            it.findNavController().navigate(MeasuringUnitsListFragmentDirections.actionMeasuringUnitsListFragmentToEditMeasuringUnitFragment(item.id, item.name))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasuringUnitsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.measuring_unit_item_view, parent, false) as CardView
        return MeasuringUnitsListViewHolder(view)
    }
}