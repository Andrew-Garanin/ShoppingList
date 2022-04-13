package com.example.shoppinglist.purchasenameslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class PurchaseNamesListViewModel(val dao: ShoppingListDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val purchaseNames = dao.getAllPurchaseNames()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onDeletePurchaseName(id: Int){
        uiScope.launch {
            removePurchaseName(id)
        }
    }

    private suspend fun removePurchaseName(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deletePurchaseName(id)
        }
    }
}