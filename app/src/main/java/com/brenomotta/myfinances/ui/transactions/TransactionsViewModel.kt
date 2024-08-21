package com.brenomotta.myfinances.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.RecurrenceModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.repository.AccountRepository
import com.brenomotta.myfinances.service.repository.TransactionRepository
import com.brenomotta.myfinances.service.util.FinancesFormatter
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import kotlin.random.Random

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository(application.applicationContext)
    private val accountRepository = AccountRepository(application.applicationContext)

    private val _listTransactions = MutableLiveData<List<TransactionModel>>()
    val listTransactions: LiveData<List<TransactionModel>> = _listTransactions

    private val _listAccounts = MutableLiveData<List<AccountModel>>()
    val listAccounts: LiveData<List<AccountModel>> = _listAccounts

    private val _listRecurrences = MutableLiveData<List<RecurrenceModel>>()
    val listRecurrences: LiveData<List<RecurrenceModel>> = _listRecurrences

    private val _transaction = MutableLiveData<TransactionModel>()
    val transaction : LiveData<TransactionModel> = _transaction

    fun loadData() {
        _listRecurrences.value = transactionRepository.listRecurrences()

        /**
         * TODO Alterar para a página de login quando a mesma for criada
         * */
        if (_listRecurrences.value!!.isEmpty()) {
            transactionRepository.create(RecurrenceModel(0, "nenhuma"))
            transactionRepository.create(RecurrenceModel(1, "diária"))
            transactionRepository.create(RecurrenceModel(2, "semanal"))
            transactionRepository.create(RecurrenceModel(3, "mensal"))
            transactionRepository.create(RecurrenceModel(4, "anual"))
        }

        _listTransactions.value = transactionRepository.listTransactions()
        _listAccounts.value = accountRepository.listAccounts()

        /**
         * Faz a associação do nome das contas na transação, caso as listas não sejam nulas
         * */
        if (!(_listTransactions.value.isNullOrEmpty() && _listAccounts.value.isNullOrEmpty())) {
            for (l in _listTransactions.value!!) {
                l.accountName = _listAccounts.value!!.filter { l.accountId == it.id }[0].description
            }
        }
    }


    fun save(transactionModel: TransactionModel) {
        if (transactionModel.id != null) {
            transactionRepository.updateTransaction(transactionModel)
        } else {
            transactionModel.codTransaction = generateCodTransaction()
            handleCreate(transactionModel)
        }
    }

    fun getAccount(id: Int): AccountModel {
        return accountRepository.getAccount(id)
    }

    fun delete (codTransaction: Int) {
        transactionRepository.delete(codTransaction)
    }

    fun getTransaction(id: Int){
        _transaction.value = transactionRepository.getTransaction(id)
    }

    private fun handleCreate(transaction: TransactionModel) {
        val limitDate = FinancesFormatter.toDate(transaction.dateOfMonth)
            .with(TemporalAdjusters.lastDayOfYear())

        do {
            transactionRepository.create(transaction)
            transaction.dateOfMonth = FinancesFormatter.toString(handleRecurrence(transaction))
            /**
             * Cria as transações de acordo com suas recorrencias
             * */
        } while (FinancesFormatter.toDate(transaction.dateOfMonth) <= limitDate
            && transaction.recurrenceId != FinancesConstants.RECURRENCE.NONE
        )

    }

    
    private fun handleRecurrence(transaction: TransactionModel): LocalDate {
        return when (transaction.recurrenceId) {
            FinancesConstants.RECURRENCE.DAILY -> FinancesFormatter.toDate(transaction.dateOfMonth)
                .plusDays(1)

            FinancesConstants.RECURRENCE.WEEKLY -> FinancesFormatter.toDate(transaction.dateOfMonth)
                .plusWeeks(1)

            FinancesConstants.RECURRENCE.MONTHLY -> FinancesFormatter.toDate(transaction.dateOfMonth)
                .plusMonths(1)

            FinancesConstants.RECURRENCE.ANNUAL -> FinancesFormatter.toDate(transaction.dateOfMonth)
                .plusYears(1)

            else -> FinancesFormatter.toDate(transaction.dateOfMonth)

        }
    }


    private fun generateCodTransaction(): Int {

        val random = Random.Default
        val listCodTransaction = transactionRepository.listCodTransaction().distinct()
        val intervalo = 1..5000
        val numbers = intervalo.toSet().subtract(listCodTransaction.toSet())

        return numbers.random(random)
    }
}