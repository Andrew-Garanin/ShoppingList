package com.example.shoppinglist.editpurchase

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.Purchase
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class EditPurchaseViewModelFactory(
    private val dao: ShoppingListDatabaseDao,
    private val application: Application,
    private val purchase: Purchase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditPurchaseViewModel::class.java)) {
            return EditPurchaseViewModel(dao, application, purchase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}