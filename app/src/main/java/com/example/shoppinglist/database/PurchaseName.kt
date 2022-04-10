package com.example.shoppinglist.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PurchaseName")
class PurchaseName (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @NonNull
    val name: String = "",

)