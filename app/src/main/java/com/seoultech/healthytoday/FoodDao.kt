package com.seoultech.healthytoday

import androidx.room.*

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(foods: List<my_table>)

    @Query("SELECT * FROM my_table")
    fun getAllFoods(): List<my_table>

    @Query("SELECT * FROM my_table LIMIT 2 OFFSET :index")
    fun getFoodAtIndex(index: Int): my_table?

    @Query("SELECT cal FROM my_table")
    fun calories(): List<String>

    @Query("SELECT protein FROM my_table")
    fun protein(): List<String>

    @Query("SELECT carbon FROM my_table")
    fun carbon(): List<String>
}