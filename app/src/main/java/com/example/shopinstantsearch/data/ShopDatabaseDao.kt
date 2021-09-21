package com.example.shopinstantsearch.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shops: List<ShopInfo>)

    @Update
    suspend fun update(shop: ShopInfo)

    @Query("SELECT * FROM shop_info_table WHERE shopId = :key")
    suspend fun get(key: Long): ShopInfo

    @Query("DELETE FROM shop_info_table")
    suspend fun clear()

    @Query("SELECT * FROM shop_info_table ORDER BY shopId")
    fun getAllShops(): LiveData<List<ShopInfo>>
}