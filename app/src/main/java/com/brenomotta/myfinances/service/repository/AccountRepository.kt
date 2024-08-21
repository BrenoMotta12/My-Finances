package com.brenomotta.myfinances.service.repository

import android.content.Context
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.RecurrenceModel
import com.brenomotta.myfinances.service.repository.local.MyFinancesDataBase

class AccountRepository(val context: Context) {

    private val financesDataBase = MyFinancesDataBase.getDatabase(context).transactionsDAO()

    fun create(accountModel: AccountModel) {
        financesDataBase.create(accountModel)
    }

    fun update(accountModel: AccountModel) {
        financesDataBase.update(accountModel.id!!, accountModel.description, accountModel.value)

    }

    fun listAccounts(): List<AccountModel> {
        return financesDataBase.listAccounts()
    }

    fun getAccount(id: Int): AccountModel {
        return financesDataBase.getAccount(id)
    }

    fun delete(id: Int) {
        financesDataBase.deleteAccount(id)
    }



}