package com.example.shoppinglist.editpurchasename

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class EditPurchaseNameViewModelFactory(private val dao: ShoppingListDatabaseDao,
                                       private val application: Application,
                                       private val purchaseNameID: Int,
                                       private val purchaseNameString: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditPurchaseNameViewModel::class.java)) {
            return EditPurchaseNameViewModel(dao, application, purchaseNameID, purchaseNameString) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}