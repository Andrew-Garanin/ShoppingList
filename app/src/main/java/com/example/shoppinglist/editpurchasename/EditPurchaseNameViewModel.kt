package com.example.shoppinglist.editpurchasename

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.database.PurchaseName
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class EditPurchaseNameViewModel(val dao: ShoppingListDatabaseDao, application: Application, purchaseNameID: Int, purchaseNameString: String) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _purchaseNameID = MutableLiveData<Int>()
    val purchaseNameID: LiveData<Int>
        get() = _purchaseNameID

    private val _purchaseNameString = MutableLiveData<String>()
    val purchaseNameString: LiveData<String>
        get() = _purchaseNameString

    init {
        _purchaseNameID.value =purchaseNameID
        _purchaseNameString.value = purchaseNameString
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onUpdatePurchaseName(updatedPurchaseNameString: String) {
        uiScope.launch {
            val updatedPurchaseName = PurchaseName(id=_purchaseNameID.value!!,name = updatedPurchaseNameString)
            updatePurchaseName(updatedPurchaseName)
        }
    }

    private suspend fun updatePurchaseName(updatedPurchaseName: PurchaseName) {
        withContext(Dispatchers.IO) {
            dao.updatePurchaseName(updatedPurchaseName)
        }
    }
}