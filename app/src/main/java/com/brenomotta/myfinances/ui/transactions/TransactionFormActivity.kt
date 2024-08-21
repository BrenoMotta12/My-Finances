package com.brenomotta.myfinances.ui.transactions

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.ActivityTransactionFormBinding
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.listener.AccountSelectionListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.util.FinancesFormatter
import com.brenomotta.myfinances.ui.modal.ModalSelectAccount
import java.util.Calendar

class TransactionFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityTransactionFormBinding
    private lateinit var viewModel: TransactionsViewModel
    private var _typeTransaction: String = FinancesConstants.TRANSACTIONS.INCOME
    private var _accountId: Int = 0
    private var _transactionId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        binding = ActivityTransactionFormBinding.inflate(layoutInflater)

        // Eventos de click
        binding.buttonDate.setOnClickListener(this)
        binding.textButtonIncomeScreenTransactionForm.setOnClickListener(this)
        binding.textButtonExpenseScreenTransactionForm.setOnClickListener(this)
        binding.viewAccountScreenTransactionForm.setOnClickListener(this)
        binding.buttonSaveScreenTransactionForm.setOnClickListener(this)

        // Mascara de valor monetario
        FinancesFormatter.maskMonetaryValue(binding.editValueTransactionScreenTransactionForm)

        viewModel.loadData()

        observe()

        supportActionBar?.hide()

        setContentView(binding.root)

        handleExtras()
    }


    private fun handleExtras() {
        /**
         * Função que verifica se algum valor foi passado na chamada da intent,
         * se sim, atribui os dados nos campos e faz as tratativas necessarias
         * */
        if (intent.extras?.getInt(FinancesConstants.BUNDLE.ID) != null) {
            viewModel.getTransaction(intent.extras?.getInt(FinancesConstants.BUNDLE.ID)!!)
        }


    }

    override fun onClick(v: View) {

        when (v.id) {
            /**
             * Seleção de data
             * */
            R.id.button_date -> handleDate()

            /**
             * Alternancia entre as cores verde e vermelho na seleção entre Receita e Despesa
             * */
            R.id.text_button_income_screen_transaction_form -> {
                setTypeTransactionSelected(FinancesConstants.TRANSACTIONS.INCOME)
            }

            R.id.text_button_expense_screen_transaction_form -> {
                setTypeTransactionSelected(FinancesConstants.TRANSACTIONS.EXPENSES)
            }

            /**
             * Seleção de conta
             * */
            R.id.view_account_screen_transaction_form -> {
                val modal = ModalSelectAccount(
                    this,
                    viewModel.listAccounts.value ?: listOf(),
                    object : AccountSelectionListener {
                        override fun onAccountSelected(account: AccountModel) {
                            attachAccount(account)
                        }
                    })
                modal.show()
            }

            /**
             * Salvar transação
             * */
            R.id.button_save_screen_transaction_form -> {
                handleSave()
            }
        }
    }

    private fun attachAccount(account: AccountModel) {
        binding.textAccountDescriptionScreenTransaction.text =
            account.description
        _accountId = account.id!!
    }

    private fun setTypeTransactionSelected(type: String) {
        if (type == FinancesConstants.TRANSACTIONS.INCOME) {
            binding.textButtonIncomeScreenTransactionForm.setTextColor(getColor(R.color.green))
            binding.textButtonExpenseScreenTransactionForm.setTextColor(getColor(R.color.black))
            binding.viewLineIncome.setBackgroundResource(R.color.green)
            binding.viewLineExpense.setBackgroundResource(R.color.grey_700)
        } else {
            binding.textButtonExpenseScreenTransactionForm.setTextColor(getColor(R.color.red))
            binding.textButtonIncomeScreenTransactionForm.setTextColor(getColor(R.color.black))
            binding.viewLineExpense.setBackgroundResource(R.color.red)
            binding.viewLineIncome.setBackgroundResource(R.color.grey_700)
        }
        _typeTransaction = type
    }

    private fun handleSave() {

        if (!verifyData()) {
            return
        }

        val transactionModel = TransactionModel().apply {
            val value = FinancesFormatter.monetaryToDouble(binding.editValueTransactionScreenTransactionForm.text.toString())
            this.id = _transactionId
            this.typeTransaction = _typeTransaction
            this.description = binding.editDescriptionTransactionScreenTransactionForm.text.toString()
            this.value = value
            this.codTransaction = viewModel.transaction.value?.codTransaction ?: 0
            this.accountId = _accountId
            this.dateOfMonth = binding.buttonDate.text.toString()
            this.recurrenceId = binding.spinnerRecurrenceScreenTransactionForm.selectedItemPosition
        }

        viewModel.save(transactionModel)
        finish()
    }

    private fun verifyData(): Boolean {
        /**
         * Verifica se os dados foram preenchidos corretamente
         * */
        if (binding.editDescriptionTransactionScreenTransactionForm.text.toString().isEmpty()) {
            showToast("Descrição não preenchida, verifique!")
            return false
        }
        if (binding.editValueTransactionScreenTransactionForm.text.toString().isEmpty()) {
            showToast("Valor não preenchido, verifique!")
            return false
        }
        if (binding.buttonDate.text.toString().isEmpty()) {
            showToast("Data não selecionada, verifique!")
            return false
        }

        return true
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.buttonDate.text = FinancesFormatter.intToDateString(year, month, dayOfMonth)
    }

    private fun handleDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, day).show()

    }

    private fun observe() {
        viewModel.listAccounts.observe(this) {
            binding.textAccountDescriptionScreenTransaction.text = it[0].description
            _accountId = it[0].id!!
        }
        viewModel.listRecurrences.observe(this) {
            val listRecurrences = mutableListOf<String>()
            for (i in it) {
                // Torna a primeira letra maiuscula e adiciona a palavra na lista
                listRecurrences.add(i.description[0].uppercase() + i.description.substring(1))
            }
            binding.spinnerRecurrenceScreenTransactionForm.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listRecurrences
            )

        }
        viewModel.transaction.observe(this) {
            _transactionId = it.id
            if (it.typeTransaction == FinancesConstants.TRANSACTIONS.EXPENSES) {
                binding.textButtonExpenseScreenTransactionForm.performClick()
            }
            binding.editDescriptionTransactionScreenTransactionForm.setText(it.description)
            binding.editValueTransactionScreenTransactionForm.setText(it.value.toString())
            binding.buttonDate.text = it.dateOfMonth
            binding.buttonDate.isEnabled = false
            binding.buttonDate.setTextColor(getColor(R.color.grey_300))
            binding.spinnerRecurrenceScreenTransactionForm.setSelection(it.recurrenceId)
            binding.spinnerRecurrenceScreenTransactionForm.isEnabled = false
            attachAccount(viewModel.getAccount(it.accountId))
        }
    }
}