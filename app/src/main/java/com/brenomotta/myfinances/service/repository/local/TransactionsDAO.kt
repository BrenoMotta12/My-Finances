package com.brenomotta.myfinances.service.repository.local

import android.provider.MediaStore.DownloadColumns
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
    fun getTransaction(id: Int): TransactionModel

    @Query("SELECT cod_transaction FROM transactions")
    fun listCodTransaction(): List<Int>

    @Query("DELETE FROM transactions WHERE cod_transaction = :codTransaction")
    fun deleteTransaction(codTransaction: Int)

    @Query("UPDATE transactions SET value = :value, typeTransaction = :typeTransaction, description = :description, account_id = :accountId WHERE cod_transaction = :codTransaction")
    fun updateTransaction(codTransaction: Int, value: Double, typeTransaction: String, description: String, accountId: Int
    )

    /**
     *  Account Model
     * */
    @Insert
    fun create(accountModel: AccountModel)

    @Query("UPDATE account SET description = :description, value = :value WHERE id = :id")
    fun update(id: Int, description: String, value: Double)

    @Query("SELECT * FROM account")
    fun listAccounts(): List<AccountModel>

    @Query("SELECT * FROM account WHERE id = :id")
    fun getAccount(id: Int): AccountModel

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