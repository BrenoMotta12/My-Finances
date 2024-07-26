package com.brenomotta.myfinances.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.repository.TransactionRepository

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TransactionRepository(application.applicationContext)

    private val _listTransactions = MutableLiveData<List<TransactionModel>>()
    val listTransactions: LiveData<List<TransactionModel>> = _listTransactions

    fun loadTransactions() {
        _listTransactions.value = repository.listTransactions()
    }
}