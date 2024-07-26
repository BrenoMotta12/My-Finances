package com.brenomotta.myfinances.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.CategoryModel
import com.brenomotta.myfinances.service.model.TransactionModel

@Database(entities = [TransactionModel::class, AccountModel::class, CategoryModel::class], version = 4)
abstract class MyFinancesDataBase : RoomDatabase() {

    abstract fun transactionsDAO(): TransactionsDAO
    companion object {
        private lateinit var INSTANCE: MyFinancesDataBase

        fun getDatabase(context: Context): MyFinancesDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(MyFinancesDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, MyFinancesDataBase::class.java, "myFinancesDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}