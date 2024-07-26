package com.brenomotta.myfinances.service.repository

import android.content.Context
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.repository.local.MyFinancesDataBase

class TransactionRepository(val context: Context) {

    private val financesDataBase = MyFinancesDataBase.getDatabase(context).transactionsDAO()


    fun listTransactions(): List<TransactionModel> {
        return financesDataBase.listTransactions()
    }
}