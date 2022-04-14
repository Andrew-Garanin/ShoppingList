package com.example.shoppinglist.purchasenameslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentPurchaseNamesListBinding

class PurchaseNamesListFragment : Fragment() {

    private lateinit var viewModel: PurchaseNamesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentPurchaseNamesListBinding>(inflater, R.layout.fragment_purchase_names_list, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //=============================Настройки ViewModel=============================
        val viewModelFactory = PurchaseNamesListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PurchaseNamesListViewModel::class.java)

        val adapter = PurchaseNamesListAdapter(viewModel)
        binding.contentList.adapter = adapter

        viewModel.purchaseNames.observe(viewLifecycleOwner, { NewpurchaseNames ->
            if (NewpurchaseNames != null)
                adapter.data = NewpurchaseNames
        })

        binding.buttonAddNewContent.setOnClickListener{
            it.findNavController().navigate(PurchaseNamesListFragmentDirections.actionPurchaseNamesListFragmentToAddNewPurchaseNameFragment())
        }

        return binding.root
    }
}