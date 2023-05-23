package com.example.myapplication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
// Define your entity class representing the table structure
@Entity()
data class Food2(
    @PrimaryKey val index: Int,
    @ColumnInfo(name = "식품명") val name: String,
    @ColumnInfo(name = "에너지") val calories: String,
    @ColumnInfo(name = "단백질") val protein: String,
    @ColumnInfo(name = "지방") val fat: String,
    @ColumnInfo(name = "탄수화물") val carbon: String,
    @ColumnInfo(name = "1회제공량") val per_1: Float
)



@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(foods: List<Food2>)



    @Query("SELECT * FROM food2")
    fun getAllFoods(): List<Food2>

    @Query("SELECT * FROM food2 LIMIT 1 OFFSET :index")
    fun getFoodAtIndex(index: Int): Food2?
}

@Database(entities = [Food2::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}

object DatabaseHelper {
    fun showToastMessage(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "food-db")
                .createFromAsset("food.db")
                .build()
            val foodDao = db.foodDao() // AppDatabase에서 실제 DAO 인터페이스를 가져옵니다.
            val foods = foodDao.getAllFoods() // DAO를 사용하여 데이터베이스에서 행을 가져옵니다. (여기서는 예시로 "getFoods()" 메서드를 사용했습니다.)






        }
    }
}
