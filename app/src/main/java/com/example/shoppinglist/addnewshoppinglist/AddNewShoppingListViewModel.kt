package com.example.shoppinglist.addnewshoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.database.ShoppingList
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class AddNewShoppingListViewModel(val dao: ShoppingListDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    fun onAddNewShoppingList(shoppingListName: String){
        val newShoppingList = ShoppingList(shopping_list_name = shoppingListName)
        uiScope.launch {
            addNewShoppingList(newShoppingList)
        }
    }

    private suspend fun addNewShoppingList(newShoppingList: ShoppingList) {
        withContext(Dispatchers.IO) {
            dao.insertShoppingList(newShoppingList)
        }
    }
}