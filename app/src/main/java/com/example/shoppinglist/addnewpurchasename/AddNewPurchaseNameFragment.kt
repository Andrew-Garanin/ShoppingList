package com.example.shoppinglist.addnewpurchasename

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentAddNewPurchaseNameBinding



class AddNewPurchaseNameFragment : DialogFragment() {
    private lateinit var viewModel: AddNewPurchaseNameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAddNewPurchaseNameBinding>(inflater,
            R.layout.fragment_add_new_purchase_name, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = AddNewPurchaseNameViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddNewPurchaseNameViewModel::class.java)

        binding.ok.setOnClickListener {
            viewModel.onAddNewPurchaseName(binding.textEdit.text.toString())
            dismiss()
        }

        binding.close.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}