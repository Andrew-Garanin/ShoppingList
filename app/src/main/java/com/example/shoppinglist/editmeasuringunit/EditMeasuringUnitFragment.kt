package com.example.shoppinglist.editmeasuringunit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentEditMeasuringUnitBinding
import com.example.shoppinglist.databinding.FragmentEditPurchaseNameBinding
import com.example.shoppinglist.editpurchasename.EditPurchaseNameFragmentArgs
import com.example.shoppinglist.editpurchasename.EditPurchaseNameViewModel
import com.example.shoppinglist.editpurchasename.EditPurchaseNameViewModelFactory

class EditMeasuringUnitFragment : DialogFragment() {
    private lateinit var viewModel: EditMeasuringUnitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentEditMeasuringUnitBinding>(inflater, R.layout.fragment_edit_measuring_unit, container, false)
        val addNewContentFragmentArgs by navArgs<EditMeasuringUnitFragmentArgs>()
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------Настройки ViewModel----------------------
        val viewModelFactory = EditMeasuringUnitViewModelFactory(dao, application, addNewContentFragmentArgs.measuringUnitID, addNewContentFragmentArgs.measuringUnitString)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditMeasuringUnitViewModel::class.java)

        binding.ok.setOnClickListener {
            val text = binding.textEdit.text.toString().trim()
            if (text != ""){
                viewModel.onUpdateMeasuringUnit(text)
                Toast.makeText(application, it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else
                Toast.makeText(application, it.context.resources.getString(R.string.enter_measuring_unit), Toast.LENGTH_SHORT).show()
        }
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.textEdit.setText(viewModel.measuringUnitString.value)

        return binding.root
    }

}