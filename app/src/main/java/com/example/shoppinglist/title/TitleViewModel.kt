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

    private val _shoppingList = MutableLiveData<List<ShoppingListDatabaseDao.PetWithOwnerAndHousehold>>()
    var shoppingList : LiveData<List<ShoppingListDatabaseDao.PetWithOwnerAndHousehold>>
//        get() = _shoppingList

    private val _shoppingListId = MutableLiveData<Int>()
    val shoppingListId: MutableLiveData<Int>
        get() = _shoppingListId

    init {
        _shoppingListId.value = shoppingListId

        shoppingList = Transformations.switchMap(_shoppingListId) { _shoppingListId ->
            filterBooks(_shoppingListId)
        }
    }

    private fun filterBooks(status: Int): LiveData<List<ShoppingListDatabaseDao.PetWithOwnerAndHousehold>> {
        return dao.getPetWithOwnerAndHousehold(status)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onGetShoppingListById(id: Int){
        uiScope.launch {
            getShoppingListById(id)
        }
    }

    private suspend fun getShoppingListById(id: Int) {
        withContext(Dispatchers.IO) {
            shoppingList = dao.getPetWithOwnerAndHousehold(id)
        }
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