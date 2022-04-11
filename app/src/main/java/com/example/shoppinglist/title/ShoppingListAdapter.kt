package com.example.shoppinglist.title

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabaseDao


class ShoppingListAdapter(var viewModel: TitleViewModel): RecyclerView.Adapter<PurchaseItemViewHolder>(){

    var data = listOf<ShoppingListDatabaseDao.PurchaseFullInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PurchaseItemViewHolder, position: Int) {
        val item = data[position]
        holder.text.text = item.purchaseName.name + " " + item.purchase.amount.toString() +item.measuringUnit.name

        holder.checkBox.isChecked = item.purchase.is_bought != 0

        holder.deleteButton.setOnClickListener {
            viewModel.onDeletePurchase(item.purchase.id)
        }

        holder.checkBox.setOnCheckedChangeListener{ buttonView, isChecked ->
            viewModel.onChangePurchaseStatus(item.purchase.id, if (isChecked) 1 else 0)
        }


        holder.card.setOnClickListener{
            val intent = Intent()
            it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToEditPurchaseFragment(item.purchase))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.purchase_item_view, parent, false) as CardView
        return PurchaseItemViewHolder(view)
    }

}