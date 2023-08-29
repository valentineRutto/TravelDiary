package com.valentinerutto.traveldiary.util

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Update

@Dao
interface BaseDao<T> {

    @Insert(onConflict= IGNORE)
    suspend fun insert(item: T)

    @Insert(onConflict = IGNORE)
    suspend fun insert(vararg items: T)

    @Insert(onConflict = IGNORE)
    suspend fun insert(items: List<T>)

    @Update(onConflict = REPLACE)
    suspend fun update(item: T): Int

    @Update(onConflict = REPLACE)
    suspend fun update(items: List<T>): Int

    @Delete
    suspend fun delete(item: T)
}