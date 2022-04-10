package com.example.shoppinglist.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MeasuringUnit")
class MeasuringUnit (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @NonNull
    val name: String = ""

) {
    override fun toString(): String {
        return name
    }
}