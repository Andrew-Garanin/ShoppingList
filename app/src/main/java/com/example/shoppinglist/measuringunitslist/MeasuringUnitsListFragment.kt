package com.example.shoppinglist.measuringunitslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentMeasuringUnitsListBinding

class MeasuringUnitsListFragment : Fragment() {
    private lateinit var viewModel: MeasuringUnitsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentMeasuringUnitsListBinding>(inflater, R.layout.fragment_measuring_units_list, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //============================Настройки ViewModel============================
        val viewModelFactory = MeasuringUnitsListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MeasuringUnitsListViewModel::class.java)

        val adapter = MeasuringUnitsListAdapter(viewModel)
        binding.contentList.adapter = adapter

        viewModel.measuringUnits.observe(viewLifecycleOwner, { NewmeasuringUnits ->
            if (NewmeasuringUnits != null)
                adapter.data = NewmeasuringUnits
        })

        binding.buttonAddNewContent.setOnClickListener{
            it.findNavController().navigate(MeasuringUnitsListFragmentDirections.actionMeasuringUnitsListFragmentToAddNewMeasuringUnitFragment())
        }

        return binding.root
    }
}