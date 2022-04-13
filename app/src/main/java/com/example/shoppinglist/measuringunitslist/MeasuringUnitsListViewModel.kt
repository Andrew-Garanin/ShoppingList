package com.example.shoppinglist.measuringunitslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class MeasuringUnitsListViewModel(val dao: ShoppingListDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val measuringUnits = dao.getAllMeasureUnits()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onDeleteMeasuringUnit(id: Int){
        uiScope.launch {
            removeMeasuringUnit(id)
        }
    }

    private suspend fun removeMeasuringUnit(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deleteMeasuringUnit(id)
        }
    }
}