package com.example.shoppinglist.editmeasuringunit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.database.ShoppingListDatabaseDao

class EditMeasuringUnitViewModelFactory(private val dao: ShoppingListDatabaseDao,
                                        private val application: Application,
                                        private val measuringUnitID: Int,
                                        private val measuringUnitString: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMeasuringUnitViewModel::class.java)) {
            return EditMeasuringUnitViewModel(dao, application, measuringUnitID, measuringUnitString) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}