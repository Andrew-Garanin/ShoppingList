package com.example.shoppinglist.addnewpurchase

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class AddNewPurchaseViewModelFactory(
    private val dao: ShoppingListDatabaseDao,
    private val application: Application,
    private val shoppingListId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewPurchaseViewModel::class.java)) {
            return AddNewPurchaseViewModel(dao, application, shoppingListId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}