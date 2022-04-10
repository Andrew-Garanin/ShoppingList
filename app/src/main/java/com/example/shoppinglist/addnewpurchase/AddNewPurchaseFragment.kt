package com.example.shoppinglist.addnewpurchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.databinding.FragmentAddNewPurchaseBinding
import android.widget.ArrayAdapter
import com.example.shoppinglist.database.PurchaseName


class AddNewPurchaseFragment : DialogFragment() {

    private lateinit var viewModel: AddNewPurchaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAddNewPurchaseBinding>(inflater,
            R.layout.fragment_add_new_purchase, container, false)
        val application = requireNotNull(this.activity).application
        val dao = ShoppingListDatabase.getInstance(application).getShoppingListDatabaseDao()

        //----------------------ViewModel----------------------
        val viewModelFactory = AddNewPurchaseViewModelFactory(dao, application, 1)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddNewPurchaseViewModel::class.java)


        viewModel.purchaseNames.observe(viewLifecycleOwner, { newPurchaseNames ->
            val arrayAdapter = context?.let { ArrayAdapter<PurchaseName>(it, R.layout.support_simple_spinner_dropdown_item, newPurchaseNames) }

            arrayAdapter?.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            binding.spinnerName.adapter = arrayAdapter
        })


        return binding.root
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
//            Log.i("asd","!!111")
//        }
//        else
//            Log.i("asd","!!2222")
//    }
}