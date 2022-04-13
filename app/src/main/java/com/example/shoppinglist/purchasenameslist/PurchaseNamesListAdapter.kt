package com.example.shoppinglist.purchasenameslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.database.PurchaseName

class PurchaseNamesListAdapter(var viewModel: PurchaseNamesListViewModel): RecyclerView.Adapter<PurchaseNamesListViewHolder>(){

    var data = listOf<PurchaseName>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PurchaseNamesListViewHolder, position: Int) {
        val item = data[position]
        holder.text.text = item.name

        holder.deleteButton.setOnClickListener {
            viewModel.onDeletePurchaseName(item.id)
            Toast.makeText(viewModel.getApplication(), "it.context.resources.getString(R.string.question_deleted_successfully)", Toast.LENGTH_SHORT).show()
        }

        holder.card.setOnClickListener{
            it.findNavController().navigate(PurchaseNamesListFragmentDirections.actionPurchaseNamesListFragmentToEditPurchaseNameFragment(item.id, item.name))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseNamesListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.purchase_name_item_view, parent, false) as CardView
        return PurchaseNamesListViewHolder(view)
    }
}