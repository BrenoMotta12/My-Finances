package com.brenomotta.myfinances.ui.home

import android.accounts.Account
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.repository.AccountRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val accountRepository = AccountRepository(application.applicationContext)

    private val _listAccounts = MutableLiveData<List<AccountModel>>()
    val listAccounts: LiveData<List<AccountModel>> = _listAccounts

    private val _currentValue = MutableLiveData<Double>()
    val currentValue: LiveData<Double> = _currentValue

    fun loadData() {

        val list = accountRepository.listAccounts()
        _listAccounts.value = list

        var value = 0.0

        for (l in list) {
            value += l.value
        }
        _currentValue.value = value
    }

    fun deleteAccount(id: Int) {
        accountRepository.delete(id)
    }
}