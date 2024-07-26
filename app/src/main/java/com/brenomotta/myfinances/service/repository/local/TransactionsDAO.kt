package com.brenomotta.myfinances.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.TransactionModel

@Dao
interface TransactionsDAO {

    /**
     * Transaction Model
     * */
    @Insert
    fun create(transactionModel: TransactionModel)

    @Query("SELECT * FROM transactions")
    fun listTransactions(): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransaction(id: Int) : TransactionModel

    /**
     *  Account Model
     * */
    @Insert
    fun create(accountModel: AccountModel)

    @Query("SELECT * FROM account")
    fun listAccounts(): List<AccountModel>

    @Query("DELETE FROM account WHERE id = :id")
    fun deleteAccount(id: Int)

}