package com.example.shoppinglist.addnewmeasuringunit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.database.MeasuringUnit
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class AddNewMeasuringUnitViewModel(val dao: ShoppingListDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    fun onAddNewMeasuringUnit(measuringUnitName: String){
        val newMeasuringUnit = MeasuringUnit(name = measuringUnitName)
        uiScope.launch {
            addNewMeasuringUnit(newMeasuringUnit)
        }
    }

    private suspend fun addNewMeasuringUnit(newMeasuringUnit: MeasuringUnit) {
        withContext(Dispatchers.IO) {
            dao.insertMeasuringUnit(newMeasuringUnit)
        }
    }
}