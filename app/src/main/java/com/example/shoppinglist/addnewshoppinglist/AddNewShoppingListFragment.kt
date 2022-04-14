package com.example.shoppinglist.addnewshoppinglist

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
import com.example.shoppinglist.databinding.FragmentAddNewShoppingListBinding


class AddNewShoppingListFragment : DialogFragment() {

    private lateinit var viewModel: AddNewShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentAddNewShoppingListBinding>(inflater,
            R.layout.fragment_add_new_shopping_list, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()


        //=======================ViewModel=======================
        val viewModelFactory = AddNewShoppingListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddNewShoppingListViewModel::class.java)

        binding.buttonAddNewContentOK.setOnClickListener {
            val text = binding.editTextAddNewContent.text.toString().trim()
            if( text != "") {
                viewModel.onAddNewShoppingList(text)
                Toast.makeText(application,  it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else
                Toast.makeText(application,  it.context.resources.getString(R.string.enter_name), Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}