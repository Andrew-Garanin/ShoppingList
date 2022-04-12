package com.example.shoppinglist.myshoppinglists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingList


class ShoppingListsAdapter(var viewModel: MyShoppingListsViewModel): RecyclerView.Adapter<ShoppingListItemViewHolder>(){
    var data = listOf<ShoppingList>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ShoppingListItemViewHolder, position: Int) {
        val item = data[position]
        holder.text.text = item.shopping_list_name

        holder.deleteButton.setOnClickListener {
            viewModel.onDeleteShoppingList(item.shopping_list_id)
        }
        holder.card.setOnClickListener{
            it.findNavController().navigate(MyShoppingListsFragmentDirections.actionMyShoppingListsFragmentToTitleFragment(item.shopping_list_id))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.shopping_list_item_view, parent, false) as CardView
        return ShoppingListItemViewHolder(view)
    }
}