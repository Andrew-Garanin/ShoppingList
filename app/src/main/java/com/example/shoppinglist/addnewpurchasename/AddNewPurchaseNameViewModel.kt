package com.example.shoppinglist.addnewpurchasename

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.database.PurchaseName
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class AddNewPurchaseNameViewModel(val dao: ShoppingListDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    fun onAddNewPurchaseName(purchaseName: String){
        val newPurchaseName = PurchaseName(name = purchaseName)
        uiScope.launch {
            addNewPurchaseName(newPurchaseName)
        }
    }

    private suspend fun addNewPurchaseName(newPurchaseName: PurchaseName) {
        withContext(Dispatchers.IO) {
            dao.insertPurchaseName(newPurchaseName)
        }
    }
}