package com.example.shoppinglist.copyshoppinglist

import android.os.Bundle
import android.util.Log
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

import com.example.shoppinglist.databinding.FragmentCopyShoppingListBinding

class CopyShoppingListFragment : DialogFragment() {

    private lateinit var viewModel: CopyShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCopyShoppingListBinding>(inflater,
            R.layout.fragment_copy_shopping_list, container, false)
        val copyShoppingListFragmentArgs by navArgs<CopyShoppingListFragmentArgs>()
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = CopyShoppingListViewModelFactory(dao, application, copyShoppingListFragmentArgs.shoppingListId)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CopyShoppingListViewModel::class.java)

        viewModel.newshoppingListId.observe(viewLifecycleOwner, {
            // Log.i("asdaw",newshoppingListId.toString())
        })

        viewModel.PurchasesToCopy.observe(viewLifecycleOwner, {newshoppingListId->

        })

        binding.buttonAddNewContentOK.setOnClickListener {
            val text = binding.editTextAddNewContent.text.toString().trim()
            if( text != "") {
                viewModel.onAddNewShoppingList(text)
                viewModel.onCopyPurchases()
                Toast.makeText(application, it.context.resources.getString(R.string.successfull), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else
                Toast.makeText(application, it.context.resources.getString(R.string.enter_name), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}