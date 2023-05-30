package com.seoultech.healthytoday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class my_table(
    @PrimaryKey val index: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cal") val cal: String,
    @ColumnInfo(name = "protein") val protein: String,
    @ColumnInfo(name = "fat") val fat: String,
    @ColumnInfo(name = "carbon") val carbon: String,
    @ColumnInfo(name = "per1") val per_1: Float
)