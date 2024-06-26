package com.example.shoppinglist.myshoppinglists

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class MyShoppingListsViewModelFactory(
  private val dao: ShoppingListDatabaseDao,
  private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyShoppingListsViewModel::class.java)) {
            return MyShoppingListsViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}