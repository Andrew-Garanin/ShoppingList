package com.example.shoppinglist.title

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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

        //----------------------ViewModel----------------------
        val viewModelFactory = TitleViewModelFactory(dao, application, 1)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TitleViewModel::class.java)

        viewModel.shoppingList.observe(viewLifecycleOwner, { newshoppingList ->
            if (newshoppingList.isNotEmpty()) {
                val actionBar = (activity as androidx.appcompat.app.AppCompatActivity?)!!.supportActionBar

                actionBar?.title = newshoppingList[0].shoppingList.shopping_list_name

                val adapter = ShoppingListAdapter(viewModel)
                binding.contentList.adapter = adapter

                adapter.data = newshoppingList
            }
        })
        viewModel.onGetShoppingListById(1)

        binding.buttonAddNewContent.setOnClickListener{
            it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAddNewPurchaseFragment())
        }

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