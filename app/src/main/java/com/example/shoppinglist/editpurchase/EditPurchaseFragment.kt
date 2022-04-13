package com.example.shoppinglist.editpurchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.shoppinglist.R
import com.example.shoppinglist.addnewpurchase.AddNewPurchaseFragmentArgs
import com.example.shoppinglist.addnewpurchase.AddNewPurchaseViewModel
import com.example.shoppinglist.addnewpurchase.AddNewPurchaseViewModelFactory
import com.example.shoppinglist.database.MeasuringUnit
import com.example.shoppinglist.database.PurchaseName
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentAddNewPurchaseBinding
import com.example.shoppinglist.databinding.FragmentEditPurchaseBinding


class EditPurchaseFragment : DialogFragment() {

    private lateinit var viewModel: EditPurchaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentEditPurchaseBinding>(inflater,
            R.layout.fragment_edit_purchase, container, false)

        val editPurchaseFragmentArgs by navArgs<EditPurchaseFragmentArgs>()
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = EditPurchaseViewModelFactory(dao, application, editPurchaseFragmentArgs.purchase)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditPurchaseViewModel::class.java)

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

            if (viewModel.purchaseNameSpinnerPosition.value == null) {
                val purchaseName = newPurchaseNames.find { it.id == viewModel.purchase.value!!.name_id }
                val index = newPurchaseNames.indexOf(purchaseName)
                binding.spinnerName.setSelection(index)

                viewModel.setPurchaseNameSpinnerPosition(binding.spinnerName.selectedItemPosition)
            }
        })

        viewModel.purchaseNameSpinnerPosition.observe(viewLifecycleOwner, { newPurchaseNameSpinnerPosition ->
            binding.spinnerName.setSelection(newPurchaseNameSpinnerPosition)
        })

        viewModel.measuringUnits.observe(viewLifecycleOwner, { newMeasuringUnits ->
            val arrayAdapter = context?.let { ArrayAdapter<MeasuringUnit>(it, R.layout.support_simple_spinner_dropdown_item, newMeasuringUnits) }

            arrayAdapter?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            binding.spinnerMeasureUnit.adapter = arrayAdapter

            if (viewModel.measuringUnitSpinnerPosition.value == null){
                val measuringUnit = newMeasuringUnits.find { it.id == viewModel.purchase.value!!.measuring_unit_id }
                val index = newMeasuringUnits.indexOf(measuringUnit)
                binding.spinnerMeasureUnit.setSelection(index)
                viewModel.setMeasuringUnitSpinnerPosition(binding.spinnerMeasureUnit.selectedItemPosition)
            }

        })

        viewModel.measuringUnitSpinnerPosition.observe(viewLifecycleOwner, { newMeasuringUnitSpinnerPosition ->
            binding.spinnerMeasureUnit.setSelection(newMeasuringUnitSpinnerPosition)
        })


        binding.textEditNumeric.setText(viewModel.purchase.value!!.amount.toString())

        binding.ok.setOnClickListener {
            val text = binding.textEditNumeric.text.toString().trim()
            if ( text != "") {
                viewModel.onEditPurchase((binding.spinnerName.selectedItem as PurchaseName).id, text.toDouble(), (binding.spinnerMeasureUnit.selectedItem as MeasuringUnit).id)
                Toast.makeText(application, it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else
                Toast.makeText(application, it.context.resources.getString(R.string.enter_count), Toast.LENGTH_SHORT).show()
        }

        binding.close.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}