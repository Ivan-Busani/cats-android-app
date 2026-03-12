package com.mivanba.catsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM cats_table")
    fun getAllCats(): Flow<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cats: List<CatEntity>)

    @Query("DELETE FROM cats_table")
    suspend fun clearAllCats()
}