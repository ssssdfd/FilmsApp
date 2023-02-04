package com.example.mymovieapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_table")
data class Film(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name = "film_title")
    val film_title:String
)
