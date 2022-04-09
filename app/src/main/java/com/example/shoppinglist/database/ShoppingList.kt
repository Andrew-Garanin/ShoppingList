package com.example.shoppinglist.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingList")
data class ShoppingList (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="shopping_list_id")
    var shopping_list_id: Int = 0,

    @ColumnInfo(name = "shopping_list_name")
    @NonNull
    val shopping_list_name: String = ""
)