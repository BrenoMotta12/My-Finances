package com.brenomotta.myfinances.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brenomotta.myfinances.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {

        viewModel = ViewModelProvider(this).get(TransactionsViewModel::class.java)
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        viewModel.loadTransactions()
        return binding.root
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}