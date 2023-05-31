package com.seoultech.healthytoday

import androidx.room.*

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(foods: List<Food>)

    @Query("SELECT * FROM Food")
    fun getAllFoods(): List<Food>

    @Query("SELECT cal FROM Food")
    fun calories(): List<String>

    @Query("SELECT protein FROM Food")
    fun protein(): List<String>

    @Query("SELECT carbon FROM Food")
    fun carbon(): List<String>

    @Query("SELECT name FROM food")
    fun nameIndex(): List<String>
}
