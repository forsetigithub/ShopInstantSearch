package com.example.shopinstantsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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
    var address2: String = ""
)

fun List<ShopInfo>.asDomainModel(): List<ShopInfo> {
    return map {
        ShopInfo(
            shopId = it.shopId,
            shopCode = it.shopCode,
            address1 = it.address1,
            address2 = it.address2
        )
    }
}