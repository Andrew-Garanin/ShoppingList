package com.example.shoppinglist.title

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class TitleViewModel(val dao: ShoppingListDatabaseDao, application: Application, shoppingListId: Int) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _shoppingList = MutableLiveData<List<ShoppingListDatabaseDao.PurchaseFullInfo>>()
    var shoppingList : LiveData<List<ShoppingListDatabaseDao.PurchaseFullInfo>>
//        get() = _shoppingList

    private val _shoppingListId = MutableLiveData<Int>()
    val shoppingListId: MutableLiveData<Int>
        get() = _shoppingListId

    private val _shoppingListName = MutableLiveData<String>()
    var shoppingListName: LiveData<String>
 //       get() = _shoppingListName

    init {
        _shoppingListId.value = shoppingListId

        shoppingList = Transformations.switchMap(_shoppingListId) { _shoppingListId ->
            refreshShoppingList(_shoppingListId)
        }

        shoppingListName = Transformations.switchMap(_shoppingListId) { _shoppingListId ->
            refreshShoppingListName(_shoppingListId)
        }
    }

    private fun refreshShoppingListName(id: Int): LiveData<String> {
        return dao.getShoppingListName(id)
    }

    private fun refreshShoppingList(status: Int): LiveData<List<ShoppingListDatabaseDao.PurchaseFullInfo>> {
        return dao.getShoppingListById(status)
    }

    fun onDeletePurchase(id: Int){
        uiScope.launch {
            removePurchase(id)
        }
    }

    private suspend fun removePurchase(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deletePurchase(id)
        }
    }

    fun onChangePurchaseStatus(id: Int, is_bought: Int) {
        uiScope.launch {
            changePurchasePurchase(id, is_bought)
        }
    }

    private suspend fun changePurchasePurchase(id: Int,is_bought: Int) {
        withContext(Dispatchers.IO) {
            dao.changeStatus(id, is_bought)
        }
    }

}