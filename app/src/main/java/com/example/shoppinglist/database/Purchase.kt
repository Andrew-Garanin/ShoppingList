package com.example.shoppinglist.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "Purchase", foreignKeys = [
    ForeignKey(entity = ShoppingList::class,
        parentColumns = ["shopping_list_id"],
        childColumns = ["shopping_list_id"],
        onDelete = CASCADE),
    ForeignKey(entity = PurchaseName::class,
        parentColumns = ["id"],
        childColumns = ["name_id"],
        onDelete = CASCADE),
    ForeignKey(entity = MeasuringUnit::class,
        parentColumns = ["id"],
        childColumns = ["measuring_unit_id"])])
data class Purchase (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="id")
    var id: Int = 0,

    @ColumnInfo(name = "name_id")
    @NonNull
    val name_id: Int = 0,

    @ColumnInfo(name = "amount")
    @NonNull
    val amount: Double = 0.0,

    @ColumnInfo(name = "is_bought")
    @NonNull
    val is_bought: Int = 0, // 0 - не куплен, 1 - куплен

    @ColumnInfo(name = "shopping_list_id")
    @NonNull
    val shopping_list_id: Int = 0,

    @ColumnInfo(name = "measuring_unit_id")
    @NonNull
    val measuring_unit_id: Int = 0,

)