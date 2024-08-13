package com.brenomotta.myfinances.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.RecurrenceModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.util.FinancesFormatter

@Database(
    entities = [TransactionModel::class, AccountModel::class, RecurrenceModel::class],
    version = 1
)
abstract class MyFinancesDataBase : RoomDatabase() {

    abstract fun transactionsDAO(): TransactionsDAO

    companion object {
        private lateinit var INSTANCE: MyFinancesDataBase

        fun getDatabase(context: Context): MyFinancesDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(MyFinancesDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MyFinancesDataBase::class.java,
                        "myFinancesDB"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}