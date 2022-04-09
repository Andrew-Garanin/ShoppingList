package com.example.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingListDatabaseDao {
    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE shopping_list_id = :key")
    fun getShoppingListById(key: Int):  LiveData<ShoppingListWithPurchases>

    data class ShoppingListWithPurchases(
        @Embedded val shoppingList: ShoppingList,
        @Relation(
            parentColumn = "shopping_list_id",
            entityColumn = "shopping_list_id"
        )
        val purchases: List<Purchase>
    )

    @Update
    fun updatePurchase(purchase: Purchase)

    @Query("UPDATE Purchase SET is_bought = :status WHERE id = :id")
    fun changeStatus(id: Int, status: Int)

    @Query("DELETE FROM Purchase WHERE id = :id")
    fun deletePurchase(id: Int)

    @Insert
    fun insertPurchase(purchase: Purchase)

    @Query("SELECT * FROM ShoppingList")
    fun getShoppingListList(): List<ShoppingList>

    @Insert
    fun insertShoppingList(shoppingList: ShoppingList)

    @Query("DELETE FROM ShoppingList WHERE shopping_list_id = :id")
    fun deleteShoppingList(id: Int)
}