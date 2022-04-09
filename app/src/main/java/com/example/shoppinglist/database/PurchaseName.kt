package com.example.shoppinglist.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "PurchaseName", foreignKeys = [
    ForeignKey(entity = MeasuringUnit::class,
        parentColumns = ["id"],
        childColumns = ["measuring_unit"],
        onDelete = ForeignKey.CASCADE
    )])
class PurchaseName (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @NonNull
    val name: String = "",

    @ColumnInfo(name = "measuring_unit")
    @NonNull
    val measuring_unit: Int = 0

)