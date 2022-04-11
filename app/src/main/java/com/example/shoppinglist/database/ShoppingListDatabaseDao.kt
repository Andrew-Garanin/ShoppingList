package com.example.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

@Dao
interface ShoppingListDatabaseDao {

    @Transaction
    @Query("Select Purchase.*, ShoppingList.shopping_list_name, PurchaseName.name, MeasuringUnit.name\n" +
            "            from Purchase LEFT OUTER JOIN ShoppingList\n" +
            "            ON Purchase.shopping_list_id = ShoppingList.shopping_list_id\n" +
            "            LEFT OUTER JOIN PurchaseName\n" +
            "            ON Purchase.name_id = PurchaseName.id\n" +
            "            LEFT OUTER JOIN MeasuringUnit\n" +
            "            ON Purchase.measuring_unit_id = MeasuringUnit.id\n" +
            "            WHERE Purchase.shopping_list_id = :key")
    fun getShoppingListById(key: Int): LiveData<List<PurchaseFullInfo>>

    data class PurchaseFullInfo (
        @Embedded
        val purchase: Purchase,
        @Relation(entity = ShoppingList::class, parentColumn = "shopping_list_id",entityColumn = "shopping_list_id")
        val shoppingList: ShoppingList,
        @Relation(entity = PurchaseName::class, parentColumn = "name_id", entityColumn = "id")
        val purchaseName: PurchaseName,
        @Relation(entity = MeasuringUnit::class, parentColumn = "measuring_unit_id", entityColumn = "id")
        val measuringUnit: MeasuringUnit
    )

    @Query("UPDATE Purchase SET is_bought = :status WHERE id = :id")
    fun changeStatus(id: Int, status: Int)

    @Query("DELETE FROM Purchase WHERE id = :id")
    fun deletePurchase(id: Int)

    @Update
    fun updatePurchase(purchase: Purchase)

    @Insert
    fun insertPurchase(purchase: Purchase)

    @Query("SELECT * FROM ShoppingList")
    fun getShoppingListList(): List<ShoppingList>

    @Insert
    fun insertShoppingList(shoppingList: ShoppingList)

    @Query("DELETE FROM ShoppingList WHERE shopping_list_id = :id")
    fun deleteShoppingList(id: Int)

    @Query("SELECT shopping_list_name FROM ShoppingList WHERE shopping_list_id = :key")
    fun getShoppingListName(key:Int): LiveData<String>

    @Query("SELECT * FROM PurchaseName")
    fun getAllPurchaseNames(): LiveData<List<PurchaseName>>

    @Query("SELECT * FROM MeasuringUnit")
    fun getAllMeasureUnits(): LiveData<List<MeasuringUnit>>
}