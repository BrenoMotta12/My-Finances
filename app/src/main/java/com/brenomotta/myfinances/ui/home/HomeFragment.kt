package com.brenomotta.myfinances.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brenomotta.myfinances.MainActivity
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.FragmentHomeBinding
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.ui.accounts.AccountsActivity
import com.brenomotta.myfinances.ui.adapter.AccountAdapter
import com.brenomotta.myfinances.ui.transactions.TransactionsFragment

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private val accountAdapter = AccountAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Instanciação da Recycler View
        binding.recyclerAccountsScreenHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerAccountsScreenHome.adapter = accountAdapter

        binding.buttonChangeBalances.setOnClickListener(this)
        binding.textNewAccount.setOnClickListener(this)

        viewModel.loadData()
        observer()

        accountAdapter.attachListener(object : RecyclerListener {
            override fun onListClick(id: Int) {}

            override fun onDeleteClick(id: Int) {
                viewModel.deleteAccount(id)
                viewModel.loadData()
            }

        })

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.button_change_balances -> {
                Log.d("Navigation", "Navigating to TransactionsFragment")
                findNavController().navigate(R.id.navigation_transactions)
            }

            R.id.text_new_account -> {
                startActivity(Intent(context, AccountsActivity::class.java))
            }
        }
    }

    private fun observer() {
        viewModel.listAccounts.observe(viewLifecycleOwner) {

            accountAdapter.updateTransactions(it)
            if (it.isNotEmpty()) {
                binding.recyclerAccountsScreenHome.visibility = View.VISIBLE
                binding.textNewAccount.setBackgroundResource(R.color.grey_100)
                binding.textNewAccount.text = "Editar Contas"
            } else {
                binding.recyclerAccountsScreenHome.visibility = View.INVISIBLE
                binding.textNewAccount.setBackgroundResource(R.drawable.background_default_white)
                binding.textNewAccount.text = "Adicionar Nova Conta"
            }
        }

        viewModel.currentValue.observe(viewLifecycleOwner) {
            binding.textValueCurrentBalance.text = "R$ " + "%.2f".format(it)
        }
    }

}