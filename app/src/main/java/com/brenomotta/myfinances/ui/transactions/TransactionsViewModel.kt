package com.brenomotta.myfinances.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.RecurrenceModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.repository.AccountRepository
import com.brenomotta.myfinances.service.repository.TransactionRepository

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository(application.applicationContext)
    private val accountRepository = AccountRepository(application.applicationContext)

    private val _listTransactions = MutableLiveData<List<TransactionModel>>()
    val listTransactions: LiveData<List<TransactionModel>> = _listTransactions

    private val _listAccounts = MutableLiveData<List<AccountModel>>()
    val listAccounts: LiveData<List<AccountModel>> = _listAccounts

    private val _listRecurrences = MutableLiveData<List<RecurrenceModel>>()
    val listRecurrences: LiveData<List<RecurrenceModel>> = _listRecurrences

    fun loadData() {


        _listRecurrences.value = transactionRepository.listRecurrences()

        /**
         * TODO Alterar para a página de login quando a mesma for criada
         * */
        if (_listRecurrences.value!!.isEmpty()) {
            transactionRepository.create(RecurrenceModel(0, "nenhuma"))
            transactionRepository.create(RecurrenceModel(1, "diária"))
            transactionRepository.create(RecurrenceModel(2,"semanal"))
            transactionRepository.create(RecurrenceModel(3,"mensal"))
            transactionRepository.create(RecurrenceModel(4,"anual"))
        }

        _listTransactions.value = transactionRepository.listTransactions()
        _listAccounts.value = accountRepository.listAccounts()

        /**
         * Faz a associação do nome das contas na transação, caso as listas não sejam nulas
         * */
        if (!(_listTransactions.value.isNullOrEmpty() && _listAccounts.value.isNullOrEmpty())) {
            for (l in _listTransactions.value!!) {
                l.accountName =
                    _listAccounts.value!!.filter { l.accountId == it.id }[0].description
            }
        }



    }

    fun save(transactionModel: TransactionModel) {
        if (transactionModel.id != null) {

        } else {
            transactionRepository.create(transactionModel)
        }
    }
}