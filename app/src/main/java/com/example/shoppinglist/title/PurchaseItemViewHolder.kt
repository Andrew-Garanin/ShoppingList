package com.example.shoppinglist.title

import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class PurchaseItemViewHolder(itemView: CardView): RecyclerView.ViewHolder(itemView) {
    val card: CardView = itemView.findViewById(R.id.cardItem)
    val text: TextView = itemView.findViewById(R.id.textItem)
    val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
}