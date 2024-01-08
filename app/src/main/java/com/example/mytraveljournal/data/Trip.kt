package com.example.mytraveljournal.data

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "trip_table")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val location: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val description: String,
    val picture: Bitmap
) : Parcelable