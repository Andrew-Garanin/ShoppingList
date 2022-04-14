package com.example.shoppinglist.addnewpurchase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

    private val _purchaseNameSpinnerPosition = MutableLiveData<Int>()
    val purchaseNameSpinnerPosition: MutableLiveData<Int>
        get() = _purchaseNameSpinnerPosition

    private val _measuringUnitSpinnerPosition = MutableLiveData<Int>()
    val measuringUnitSpinnerPosition: MutableLiveData<Int>
        get() = _measuringUnitSpinnerPosition

    init {
        _shoppingListId.value = shoppingListId
    }

    fun setPurchaseNameSpinnerPosition(position: Int){
        _purchaseNameSpinnerPosition.value = position
    }

    fun setMeasuringUnitSpinnerPosition(position: Int){
        _measuringUnitSpinnerPosition.value = position
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