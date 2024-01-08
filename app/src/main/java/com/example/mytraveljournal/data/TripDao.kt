package com.example.mytraveljournal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TripDao {

    @Insert
    suspend fun addTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Query("DELETE FROM trip_table")
    suspend fun deleteAllTrips()

    @Query("SELECT * FROM trip_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Trip>>
}