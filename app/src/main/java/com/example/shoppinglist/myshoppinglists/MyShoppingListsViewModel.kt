package com.example.shoppinglist.myshoppinglists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class MyShoppingListsViewModel(val dao: ShoppingListDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    var shoppingLists = dao.getShoppingLists()

    fun onDeleteShoppingList(id: Int){
        uiScope.launch {
            removeShoppingList(id)
        }
    }

    private suspend fun removeShoppingList(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deleteShoppingList(id)
        }
    }
}