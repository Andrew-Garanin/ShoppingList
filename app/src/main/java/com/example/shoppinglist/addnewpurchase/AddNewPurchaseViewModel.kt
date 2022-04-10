package com.example.shoppinglist.addnewpurchase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddNewPurchaseViewModel(val dao: ShoppingListDatabaseDao, application: Application, shoppingListId: Int) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    var purchaseNames = dao.getAllPurchaseNames()
    var measureUnits = dao.getAllMeasureUnits()

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
}