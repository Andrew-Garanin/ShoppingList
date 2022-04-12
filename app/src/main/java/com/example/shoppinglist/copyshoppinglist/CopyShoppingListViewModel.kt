package com.example.shoppinglist.copyshoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.database.ShoppingList
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class CopyShoppingListViewModel(val dao: ShoppingListDatabaseDao, application: Application, shoppingListId: Int) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _shoppingListId = MutableLiveData<Int>()
    val shoppingListId: MutableLiveData<Int>
        get() = _shoppingListId

    private val _newshoppingListId = MutableLiveData<Int>()
    var newshoppingListId: LiveData<Int>
        get() = _newshoppingListId

    init{
        _shoppingListId.value = shoppingListId

        newshoppingListId = Transformations.switchMap(_shoppingListId) {d ->
            getLastShoppingListId()
        }
    }

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

//    fun onGetLastShoppingListId(){
//        uiScope.launch {
//            getLastShoppingListId()
//        }
//    }

    private fun getLastShoppingListId(): LiveData<Int> {
        return dao.getLastShoppingListId()
    }
}