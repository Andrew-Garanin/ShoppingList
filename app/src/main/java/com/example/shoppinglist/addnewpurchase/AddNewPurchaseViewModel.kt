package com.example.shoppinglist.addnewpurchase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.database.Purchase
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class AddNewPurchaseViewModel(val dao: ShoppingListDatabaseDao, application: Application, shoppingListId: Int) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    var purchaseNames = dao.getAllPurchaseNames()
    var measuringUnits = dao.getAllMeasureUnits()

    private val _shoppingListId = MutableLiveData<Int>()
    val shoppingListId: MutableLiveData<Int>
        get() = _shoppingListId

    init {
        _shoppingListId.value = shoppingListId
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onInsertPurchase(name_id: Int, amount: Double, measuring_unit_id: Int){
        val newPurchase = Purchase(name_id=name_id, amount=amount, is_bought = 0, shopping_list_id = shoppingListId.value!!, measuring_unit_id = measuring_unit_id)
        uiScope.launch {
            insertPurchase(newPurchase)
        }
    }

    private suspend fun insertPurchase(newPurchase: Purchase) {
        withContext(Dispatchers.IO) {
            dao.insertPurchase(newPurchase)
        }
    }
}