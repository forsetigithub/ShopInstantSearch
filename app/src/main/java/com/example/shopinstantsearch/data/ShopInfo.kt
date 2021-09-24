package com.example.shopinstantsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey


@Entity(tableName = "shop_info_table")
data class ShopInfo (
    @PrimaryKey(autoGenerate = true)
    var shopId: Long = 0L,

    @ColumnInfo(name = "shop_code")
    var shopCode: String = "",

    @ColumnInfo(name = "address1")
    var address1: String = "",

    @ColumnInfo(name = "address2")
    var address2: String = "",
)

