package com.example.shoppinglist.addnewmeasuringunit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.addnewpurchasename.AddNewPurchaseNameViewModel
import com.example.shoppinglist.addnewpurchasename.AddNewPurchaseNameViewModelFactory
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentAddNewMeasuringUnitBinding
import com.example.shoppinglist.databinding.FragmentAddNewPurchaseNameBinding

class AddNewMeasuringUnitFragment : DialogFragment() {
    private lateinit var viewModel: AddNewMeasuringUnitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddNewMeasuringUnitBinding>(inflater,
            R.layout.fragment_add_new_measuring_unit, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = AddNewMeasuringUnitViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddNewMeasuringUnitViewModel::class.java)

        binding.ok.setOnClickListener {
            val text = binding.textEdit.text.toString().trim()
            if (text != "") {
                viewModel.onAddNewMeasuringUnit(text)
                Toast.makeText(application, it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else
                Toast.makeText(application, it.context.resources.getString(R.string.enter_measuring_unit), Toast.LENGTH_SHORT).show()

        }

        binding.close.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}