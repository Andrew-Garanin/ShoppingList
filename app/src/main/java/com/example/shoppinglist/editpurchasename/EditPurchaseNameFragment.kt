package com.example.shoppinglist.editpurchasename

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.shoppinglist.databinding.FragmentEditPurchaseNameBinding

class EditPurchaseNameFragment : DialogFragment() {

    private lateinit var viewModel: EditPurchaseNameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentEditPurchaseNameBinding>(inflater, R.layout.fragment_edit_purchase_name, container, false)
        val addNewContentFragmentArgs by navArgs<EditPurchaseNameFragmentArgs>()
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------Настройки ViewModel----------------------
        val viewModelFactory = EditPurchaseNameViewModelFactory(dao, application, addNewContentFragmentArgs.purchaseNameID, addNewContentFragmentArgs.purchaseNameString)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditPurchaseNameViewModel::class.java)

        binding.ok.setOnClickListener {
            val text = binding.textEdit.text.toString().trim()
            if (text != ""){
                viewModel.onUpdatePurchaseName(text)
                Toast.makeText(application, "it.context.resources.getString(R.string.question_added_successfully)", Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            }
        }
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.textEdit.setText(viewModel.purchaseNameString.value)
        return binding.root
    }
}