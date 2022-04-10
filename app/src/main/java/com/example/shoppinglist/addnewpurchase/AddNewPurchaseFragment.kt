package com.example.shoppinglist.addnewpurchase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentAddNewPurchaseBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shoppinglist.database.MeasuringUnit
import com.example.shoppinglist.database.PurchaseName
import com.example.shoppinglist.title.TitleFragmentDirections


class AddNewPurchaseFragment : DialogFragment() {

    private lateinit var viewModel: AddNewPurchaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAddNewPurchaseBinding>(inflater,
            R.layout.fragment_add_new_purchase, container, false)
        val addNewPurchaseFragmentArgs by navArgs<AddNewPurchaseFragmentArgs>()
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = AddNewPurchaseViewModelFactory(dao, application, addNewPurchaseFragmentArgs.shoppingListId)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddNewPurchaseViewModel::class.java)


        viewModel.purchaseNames.observe(viewLifecycleOwner, { newPurchaseNames ->
            val arrayAdapter = context?.let { ArrayAdapter<PurchaseName>(it, R.layout.support_simple_spinner_dropdown_item, newPurchaseNames) }

            arrayAdapter?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            binding.spinnerName.adapter = arrayAdapter
        })

        viewModel.measuringUnits.observe(viewLifecycleOwner, { newMeasuringUnits ->
            val arrayAdapter = context?.let { ArrayAdapter<MeasuringUnit>(it, R.layout.support_simple_spinner_dropdown_item, newMeasuringUnits) }

            arrayAdapter?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            binding.spinnerMeasureUnit.adapter = arrayAdapter
        })

        binding.ok.setOnClickListener {
            if ( binding.textEditNumeric.text.toString() != "")
            {
                viewModel.onInsertPurchase((binding.spinnerName.selectedItem as PurchaseName).id, binding.textEditNumeric.text.toString().toDouble(), (binding.spinnerMeasureUnit.selectedItem as MeasuringUnit).id)
                dismiss()
            }
            else
                Toast.makeText(application, "asdasd", Toast.LENGTH_SHORT).show()

        }

        binding.close.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}