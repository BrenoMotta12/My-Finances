package com.brenomotta.myfinances.service.repository

import android.content.Context
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.repository.local.MyFinancesDataBase

class AccountRepository(val context: Context) {

    private val financesDataBase = MyFinancesDataBase.getDatabase(context).transactionsDAO()

    fun create(accountModel: AccountModel) {
        financesDataBase.create(accountModel)
    }

    fun list(): List<AccountModel> {
        return financesDataBase.listAccounts()
    }

    fun delete(id: Int) {
        financesDataBase.deleteAccount(id)
    }


}