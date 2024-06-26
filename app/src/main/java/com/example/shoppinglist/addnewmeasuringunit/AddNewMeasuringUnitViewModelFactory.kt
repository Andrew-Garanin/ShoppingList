package com.example.shoppinglist.addnewmeasuringunit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class AddNewMeasuringUnitViewModelFactory (
    private val dao: ShoppingListDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewMeasuringUnitViewModel::class.java)) {
            return AddNewMeasuringUnitViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}