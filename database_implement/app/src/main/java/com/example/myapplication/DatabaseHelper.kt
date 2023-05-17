package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Entity
data class Food2(
    @PrimaryKey val index: Int,

    @ColumnInfo(name = "식품명") val name: String,
    @ColumnInfo(name = "에너지") val calories: String,
    @ColumnInfo(name = "단백질") val protein: String,
    @ColumnInfo(name = "지방") val fat: String,
    @ColumnInfo(name = "탄수화물") val carbon: String,
    @ColumnInfo(name = "1회제공량") val per_1: Float,
)


@Dao
interface FoodDao {
    @Query("SELECT * FROM food2")
    fun getAll(): List<Food2>
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

            val columnNames = db.query("SELECT * FROM food LIMIT 0", null).columnNames
            val columns = columnNames.joinToString(", ")

            withContext(Dispatchers.Main) {
                Toast.makeText(context, columns, Toast.LENGTH_SHORT).show()
            }
        }
    }
}