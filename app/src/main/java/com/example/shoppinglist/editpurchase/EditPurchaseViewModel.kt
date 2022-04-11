package com.example.shoppinglist.editpurchase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.database.Purchase
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class EditPurchaseViewModel(val dao: ShoppingListDatabaseDao, application: Application, purchase: Purchase) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    var purchaseNames = dao.getAllPurchaseNames()
    var measuringUnits = dao.getAllMeasureUnits()

    private val _purchase = MutableLiveData<Purchase>()
    val purchase: MutableLiveData<Purchase>
        get() = _purchase

    private val _purchaseNameSpinnerPosition = MutableLiveData<Int>()
    val purchaseNameSpinnerPosition: MutableLiveData<Int>
        get() = _purchaseNameSpinnerPosition

    private val _measuringUnitSpinnerPosition = MutableLiveData<Int>()
    val measuringUnitSpinnerPosition: MutableLiveData<Int>
        get() = _measuringUnitSpinnerPosition

    init {
        _purchase.value = purchase
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

    fun onEditPurchase(name_id: Int, amount: Double, measuring_unit_id: Int){
        val newPurchase = Purchase(id=_purchase.value!!.id,
                                   name_id=name_id, amount=amount,
                                   is_bought = _purchase.value!!.is_bought,
                                   shopping_list_id = _purchase.value!!.shopping_list_id,
                                   measuring_unit_id = measuring_unit_id)
        uiScope.launch {
            editPurchase(newPurchase)
        }
    }

    private suspend fun editPurchase(newPurchase: Purchase) {
        withContext(Dispatchers.IO) {
            dao.updatePurchase(newPurchase)
        }
    }
}