package com.brenomotta.myfinances.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class CategoryModel {

    @PrimaryKey
    @ColumnInfo("id")
    var id: Int = 0

    @ColumnInfo("description")
    var description: String = ""

    @ColumnInfo("color")
    var color: String = ""
}