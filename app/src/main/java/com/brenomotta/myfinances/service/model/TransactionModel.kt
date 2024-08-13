package com.brenomotta.myfinances.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.brenomotta.myfinances.service.constants.FinancesConstants
import java.time.LocalDate

@Entity(
    tableName = "transactions", foreignKeys = [
        ForeignKey(
            entity = AccountModel::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ), ForeignKey(
            entity = RecurrenceModel::class,
            parentColumns = ["id"],
            childColumns = ["recurrenceId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class TransactionModel {

    @ColumnInfo("id")
    @PrimaryKey
    var id: Int? = 0

    @ColumnInfo("value")
    var value: Double = 0.0

    @ColumnInfo("typeTransaction")
    var typeTransaction: String = ""

    @ColumnInfo("description")
    var description: String = ""

    @ColumnInfo("date")
    var dateOfMonth: String = ""

    @ColumnInfo("recurrenceId")
    var recurrenceId: Int = 0

    @ColumnInfo("accountId")
    var accountId: Int = 0

    var accountName: String = ""
}
