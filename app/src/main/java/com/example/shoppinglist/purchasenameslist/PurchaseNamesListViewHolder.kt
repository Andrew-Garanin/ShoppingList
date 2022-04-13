package com.example.shoppinglist.purchasenameslist

import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class PurchaseNamesListViewHolder(itemView: CardView): RecyclerView.ViewHolder(itemView) {
    val card: CardView = itemView.findViewById(R.id.cardItemPurchaseName)
    val text: TextView = itemView.findViewById(R.id.textItemPurchaseName)
    val deleteButton: Button = itemView.findViewById(R.id.deleteButtonPurchaseName)
}