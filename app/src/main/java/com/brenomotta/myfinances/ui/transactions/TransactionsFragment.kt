package com.brenomotta.myfinances.ui.transactions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import java.util.Calendar

class TransactionsFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionsViewModel
    private val adapter = TransactionAdapter()
    private var month = Calendar.getInstance().get(Calendar.MONTH)
    private var currentList: List<TransactionModel> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {

        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        binding.recyclerTransactions.layoutManager = LinearLayoutManager(context)
        binding.recyclerTransactions.adapter = adapter

        binding.spinnerMonthScreenTransaction.setSelection(month)
        binding.spinnerMonthScreenTransaction.onItemSelectedListener = this

        binding.buttonNewTransactions.setOnClickListener(this)

        observer()

        adapter.attachContext(requireContext())
        adapter.attachListener(object : RecyclerListener {

            override fun onListClick(id: Int) {
                val intent = Intent(context, TransactionFormActivity::class.java)
                intent.putExtra(FinancesConstants.BUNDLE.ID, id)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                viewModel.delete(id)
                viewModel.loadData()
            }
        })

        viewModel.loadData()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // observers
    private fun observer() {
        viewModel.listTransactions.observe(viewLifecycleOwner) {
            currentList = it
            adapter.update(getListFilteredByMonth())
            updateTextValues()
        }
    }

    // Função chamada para atualizar os valores das receitas e despesas
    private fun updateTextValues() {
        var income: Double = 0.0
        var expense: Double = 0.0
        val list = getListFilteredByMonth()
        for (l in list) {
            if (l.typeTransaction == FinancesConstants.TRANSACTIONS.INCOME) {
                income += l.value
            } else {
                expense += l.value
            }
        }
        binding.textValueIncomeScreenTransactions.text = FinancesFormatter.maskMonetaryValue(income)
        binding.textValueExpensesScreenTransactions.text =
            FinancesFormatter.maskMonetaryValue(expense)
    }

    // Evento de clique dos componentes
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

    override fun onItemSelected(
        parent: AdapterView<*>,
        view: View,
        month: Int, // month = position
        id: Long
    ) {
        this.month = month
        adapter.update(getListFilteredByMonth())
        updateTextValues()
    }

    private fun getListFilteredByMonth(): List<TransactionModel> {

        val list = currentList.toMutableList()
        list.removeIf {
            FinancesFormatter.toDate(it.dateOfMonth).month.ordinal != month
        }
        return list
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}