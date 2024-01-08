package com.example.mytraveljournal.data

import androidx.lifecycle.LiveData

class TripRepository(private val tripDao: TripDao) {

    val readAllData: LiveData<List<Trip>> = tripDao.readAllData()

    suspend fun addTrip(trip: Trip) {
        tripDao.addTrip(trip)
    }

    suspend fun updateTrip(trip: Trip) {
        tripDao.updateTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip) {
        tripDao.deleteTrip(trip)
    }

    suspend fun deleteAllTrips() {
        tripDao.deleteAllTrips()
    }
}