package com.example.shoppinglist.measuringunitslist

import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MeasuringUnitsListViewHolder(itemView: CardView): RecyclerView.ViewHolder(itemView) {
    val card: CardView = itemView.findViewById(R.id.cardItemMeasuringUnit)
    val text: TextView = itemView.findViewById(R.id.textItemMeasuringUnit)
    val deleteButton: Button = itemView.findViewById(R.id.deleteButtonMeasuringUnit)
}