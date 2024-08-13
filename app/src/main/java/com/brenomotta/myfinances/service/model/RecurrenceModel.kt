package com.brenomotta.myfinances.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brenomotta.myfinances.service.constants.FinancesConstants

@Entity("recurrence")
class RecurrenceModel() {

    constructor(id: Int, description: String) : this() {
        this.id = id
        this.description = description
    }

    @PrimaryKey
    @ColumnInfo("id")
    var id: Int = 0

    @ColumnInfo("description")
    var description: String = ""

}