package com.example.shoppinglist.editmeasuringunit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.database.MeasuringUnit
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import kotlinx.coroutines.*

class EditMeasuringUnitViewModel(val dao: ShoppingListDatabaseDao, application: Application, measuringUnitID: Int, measuringUnitString: String) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _measuringUnitID = MutableLiveData<Int>()
    val measuringUnitID: LiveData<Int>
        get() = _measuringUnitID

    private val _measuringUnitString = MutableLiveData<String>()
    val measuringUnitString: LiveData<String>
        get() = _measuringUnitString

    init {
        _measuringUnitID.value =measuringUnitID
        _measuringUnitString.value = measuringUnitString
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onUpdateMeasuringUnit(updatedMeasuringUnitString: String) {
        uiScope.launch {
            val updatedMeasuringUnit = MeasuringUnit(id=_measuringUnitID.value!!,name = updatedMeasuringUnitString)
            updateMeasuringUnit(updatedMeasuringUnit)
        }
    }

    private suspend fun updateMeasuringUnit(updatedMeasuringUnit: MeasuringUnit) {
        withContext(Dispatchers.IO) {
            dao.updateMeasuringUnit(updatedMeasuringUnit)
        }
    }
}