package com.brenomotta.myfinances.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.RecurrenceModel
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

    @Query("UPDATE account SET description = :description, value = :value WHERE id = :id")
    fun update(id: Int, description: String, value: Double)

    @Query("SELECT * FROM account")
    fun listAccounts(): List<AccountModel>

    @Query("DELETE FROM account WHERE id = :id")
    fun deleteAccount(id: Int)

    /**
     * Recurrence Model
     * */
    @Query("SELECT * FROM recurrence")
    fun listRecurrence(): List<RecurrenceModel>

    @Insert
    fun create(recurrenceModel: RecurrenceModel)
}