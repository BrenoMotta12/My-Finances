package com.brenomotta.myfinances.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
class AccountModel {
    @PrimaryKey
    @ColumnInfo("id")
    var id: Int? = null

    @ColumnInfo("description")
    var description: String = ""

    @ColumnInfo("color")
    var color: String = ""

    @ColumnInfo("value")
    var value : Double = 0.0
}