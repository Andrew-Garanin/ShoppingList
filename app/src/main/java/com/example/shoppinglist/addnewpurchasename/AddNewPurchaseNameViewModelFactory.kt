package com.example.shoppinglist.addnewpurchasename

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class AddNewPurchaseNameViewModelFactory (
    private val dao: ShoppingListDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewPurchaseNameViewModel::class.java)) {
            return AddNewPurchaseNameViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}