package com.example.shoppinglist.addnewpurchasename

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    ): View {

        val binding = DataBindingUtil.inflate<FragmentAddNewPurchaseNameBinding>(inflater,
            R.layout.fragment_add_new_purchase_name, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //======================ViewModel======================
        val viewModelFactory = AddNewPurchaseNameViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddNewPurchaseNameViewModel::class.java)

        binding.ok.setOnClickListener {
            val text = binding.textEdit.text.toString().trim()
            if (text != ""){
                viewModel.onAddNewPurchaseName(text)
                Toast.makeText(application, it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else
                Toast.makeText(application, it.context.resources.getString(R.string.enter_name), Toast.LENGTH_SHORT).show()
        }

        binding.close.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}