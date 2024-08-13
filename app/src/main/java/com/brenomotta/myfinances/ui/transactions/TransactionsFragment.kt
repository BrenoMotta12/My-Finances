package com.brenomotta.myfinances.ui.transactions

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.FragmentTransactionsBinding
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.util.FinancesFormatter
import com.brenomotta.myfinances.ui.accounts.AccountsActivity
import com.brenomotta.myfinances.ui.adapter.TransactionAdapter

class TransactionsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionsViewModel
    private val adapter = TransactionAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {

        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        binding.recyclerTransactions.layoutManager = LinearLayoutManager(context)
        binding.recyclerTransactions.adapter = adapter

        binding.buttonNewTransactions.setOnClickListener(this)

        observer()

        adapter.attachContext(requireContext())
        adapter.attachListener(object : RecyclerListener {

            override fun onListClick(accountModel: AccountModel) {

            }

            override fun onDeleteClick(id: Int) {

            }
        })

        viewModel.loadData()
        return binding.root
    }

    private fun observer() {
        viewModel.listTransactions.observe(viewLifecycleOwner) {
            adapter.update(it)
            updateValues(it)
        }
    }

    private fun updateValues(it: List<TransactionModel>) {
        var income: Double = 0.0
        var expense: Double = 0.0
        for (i in it) {
            if (i.typeTransaction == FinancesConstants.TRANSACTIONS.INCOME) {
                income += i.value
            } else {
                expense += i.value
            }
        }

        val currentBalance = income - expense
        binding.textValueIncomeScreenTransactions.text =
            FinancesFormatter.maskMonetaryValue(income)
        binding.textValueExpensesScreenTransactions.text =
            FinancesFormatter.maskMonetaryValue(expense)
        binding.textValueCurrentBalanceScreenTransactions.text =
            FinancesFormatter.maskMonetaryValue(currentBalance)
        if (currentBalance < 0) {
            binding.textValueCurrentBalanceScreenTransactions.setTextColor(requireContext().getColor(R.color.red))
        } else {
            binding.textValueCurrentBalanceScreenTransactions.setTextColor(requireContext().getColor(R.color.green))
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_new_transactions -> {
                val list = viewModel.listAccounts.value
                if (list?.isNotEmpty() == true) {
                    val intent = Intent(context, TransactionFormActivity::class.java)
                    startActivity(intent)
                } else {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Ops...")
                        .setMessage("Para cadastrar uma nova transação, ao menos uma conta deve ser criada")
                        .setPositiveButton("Cadastrar agora") { dialog, which ->
                            startActivity(
                                Intent(
                                    context,
                                    AccountsActivity::class.java
                                ),
                            )
                        }
                        .setNeutralButton("OK", null)
                        .show()
                }
            }
        }
    }
}