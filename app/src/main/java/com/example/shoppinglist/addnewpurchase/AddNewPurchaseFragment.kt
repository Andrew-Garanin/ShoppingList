package com.example.shoppinglist.addnewpurchase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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


        binding.spinnerName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setPurchaseNameSpinnerPosition(binding.spinnerName.selectedItemPosition)
            }
        }

        binding.spinnerMeasureUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setMeasuringUnitSpinnerPosition(binding.spinnerMeasureUnit.selectedItemPosition)
            }
        }

        viewModel.purchaseNames.observe(viewLifecycleOwner, { newPurchaseNames ->
            val arrayAdapter = context?.let { ArrayAdapter<PurchaseName>(it, R.layout.support_simple_spinner_dropdown_item, newPurchaseNames) }
            arrayAdapter?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            binding.spinnerName.adapter = arrayAdapter

            if (viewModel.purchaseNameSpinnerPosition.value == null)
                viewModel.setPurchaseNameSpinnerPosition(binding.spinnerName.selectedItemPosition)
            })

        viewModel.purchaseNameSpinnerPosition.observe(viewLifecycleOwner, { newPurchaseNameSpinnerPosition ->
            binding.spinnerName.setSelection(newPurchaseNameSpinnerPosition)
        })

        viewModel.measuringUnits.observe(viewLifecycleOwner, { newMeasuringUnits ->
            val arrayAdapter = context?.let { ArrayAdapter<MeasuringUnit>(it, R.layout.support_simple_spinner_dropdown_item, newMeasuringUnits) }

            arrayAdapter?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            binding.spinnerMeasureUnit.adapter = arrayAdapter

            if (viewModel.measuringUnitSpinnerPosition.value == null)
                viewModel.setMeasuringUnitSpinnerPosition(binding.spinnerMeasureUnit.selectedItemPosition)
        })

        viewModel.measuringUnitSpinnerPosition.observe(viewLifecycleOwner, { newMeasuringUnitSpinnerPosition ->
            binding.spinnerMeasureUnit.setSelection(newMeasuringUnitSpinnerPosition)
        })

        binding.ok.setOnClickListener {
            if ( binding.textEditNumeric.text.toString() != "") {
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