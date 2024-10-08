package com.brenomotta.myfinances.ui.accounts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.repository.AccountRepository

class AccountsViewModel(application: Application) : AndroidViewModel(application) {

    private val accountRepository = AccountRepository(application.applicationContext)

    private val _listAccounts = MutableLiveData<List<AccountModel>>()
    val listAccount: LiveData<List<AccountModel>> = _listAccounts

    fun loadData() {
        _listAccounts.value = accountRepository.listAccounts()
    }

    fun save(accountModel: AccountModel) {
        if (accountModel.id == null) {
            accountRepository.create(accountModel)
        } else
            accountRepository.update(accountModel)
    }

    fun getAccount(id: Int): AccountModel {
        return accountRepository.getAccount(id)
    }

    fun deleteAccount(id: Int) {
        accountRepository.delete(id)
    }

}