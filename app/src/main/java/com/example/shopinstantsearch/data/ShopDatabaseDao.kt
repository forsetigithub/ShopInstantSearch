package com.example.shopinstantsearch.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

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
    fun getAllShops(): Flow<List<ShopInfo>>

    @Query("SELECT * FROM shop_info_table WHERE address1 LIKE :query")
    fun search(query: String) : Flow<List<ShopInfo>>

}