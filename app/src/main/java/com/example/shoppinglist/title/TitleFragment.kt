package com.example.shoppinglist.title

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private lateinit var viewModel: TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------Настройки ViewModel----------------------
        val viewModelFactory = TitleViewModelFactory(dao, application, 1)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TitleViewModel::class.java)

        viewModel.shoppingList.observe(viewLifecycleOwner, { newshoppingList ->

            val actionBar = (activity as AppCompatActivity?)!!.supportActionBar

            actionBar?.title = newshoppingList.shoppingList.shopping_list_name

            val adapter = ShoppingListAdapter(viewModel)
            binding.contentList.adapter = adapter

            if (newshoppingList != null)
                adapter.data = newshoppingList.purchases
        })
        viewModel.onGetShoppingListById(1)


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  NavigationUI.onNavDestinationSelected(item, findNavController()) ||  super.onOptionsItemSelected(item)
    }


}