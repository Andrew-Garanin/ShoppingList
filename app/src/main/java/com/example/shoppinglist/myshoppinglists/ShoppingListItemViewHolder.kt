package com.example.shoppinglist.myshoppinglists

import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShoppingListItemViewHolder(itemView: CardView): RecyclerView.ViewHolder(itemView) {
    val card: CardView = itemView.findViewById(R.id.cardItemShoppingList)
    val text: TextView = itemView.findViewById(R.id.textItemShoppingList)
    val deleteButton: Button = itemView.findViewById(R.id.deleteButtonShoppingList)
    val copyButton: Button = itemView.findViewById(R.id.copyButtonShoppingList)
}