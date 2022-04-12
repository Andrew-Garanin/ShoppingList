package com.example.shoppinglist.myshoppinglists

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
import com.example.shoppinglist.databinding.FragmentMyShoppingListsBinding
import com.example.shoppinglist.databinding.FragmentTitleBinding
import com.example.shoppinglist.title.ShoppingListAdapter
import com.example.shoppinglist.title.TitleFragmentDirections
import com.example.shoppinglist.title.TitleViewModel
import com.example.shoppinglist.title.TitleViewModelFactory

class MyShoppingListsFragment : Fragment() {

    private lateinit var viewModel: MyShoppingListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMyShoppingListsBinding>(inflater,
            R.layout.fragment_my_shopping_lists, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = MyShoppingListsViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MyShoppingListsViewModel::class.java)

        val adapter = ShoppingListsAdapter(viewModel)
        binding.contentList.adapter = adapter

        viewModel.shoppingLists.observe(viewLifecycleOwner, { newshoppingLists ->
            if (newshoppingLists.isNotEmpty())
                adapter.data = newshoppingLists
        })

        binding.buttonAddNewContent.setOnClickListener{
            it.findNavController().navigate(MyShoppingListsFragmentDirections.actionMyShoppingListsFragmentToAddNewShoppingListFragment())
        }

        return binding.root
    }
}