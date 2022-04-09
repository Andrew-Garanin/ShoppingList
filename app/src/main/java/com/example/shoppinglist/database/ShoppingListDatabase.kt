package com.example.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Purchase::class, ShoppingList::class, MeasuringUnit::class, PurchaseName::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase: RoomDatabase() {

    abstract fun getShoppingListDatabaseDao(): ShoppingListDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingListDatabase? = null

        fun getInstance(context: Context): ShoppingListDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ShoppingListDatabase::class.java, "shopping_list").createFromAsset("shopping_list.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}