package com.example.shoppinglist.copyshoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.database.Purchase
import com.example.shoppinglist.database.ShoppingList
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class CopyShoppingListViewModel(val dao: ShoppingListDatabaseDao, application: Application, shoppingListId: Int) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _shoppingListId = MutableLiveData<Int>()
    val shoppingListId: MutableLiveData<Int>
        get() = _shoppingListId

    var newshoppingListId = dao.getLastShoppingListId()
    var PurchasesToCopy : LiveData<List<Purchase>>

    init{
        _shoppingListId.value = shoppingListId

        PurchasesToCopy = Transformations.switchMap(_shoppingListId) {_shoppingListId ->
            getPurchasesByShoppingListID(_shoppingListId)
        }
    }

    fun onAddNewShoppingList(shoppingListName: String){
        val newShoppingList = ShoppingList(shopping_list_id = newshoppingListId.value!!+1,shopping_list_name = shoppingListName)
        uiScope.launch {
            addNewShoppingList(newShoppingList)
        }
    }

    private suspend fun addNewShoppingList(newShoppingList: ShoppingList) {
        withContext(Dispatchers.IO) {
            dao.insertShoppingList(newShoppingList)
        }
    }

    private fun getPurchasesByShoppingListID(key: Int): LiveData<List<Purchase>> {
        return dao.getPurchasesByShoppingListID(key)
    }

    fun onCopyPurchases(){
        PurchasesToCopy.value?.forEach{
            val newPurchase = it.copy(id=0,shopping_list_id = newshoppingListId.value!!+1)
            uiScope.launch {
                addNewPurchase(newPurchase)
            }
        }
    }

    private suspend fun addNewPurchase(purchase: Purchase) {
        withContext(Dispatchers.IO) {
            dao.insertPurchase(purchase)
        }
    }
}