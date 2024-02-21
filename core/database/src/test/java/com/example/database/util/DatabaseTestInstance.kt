package com.example.database.util

import android.content.Context
import androidx.room.Room
import com.nexusfalcao.database.ReceptIaDatabase

fun DatabaseTestInstance(context: Context): ReceptIaDatabase {
    return Room.inMemoryDatabaseBuilder(
        context,
        ReceptIaDatabase::class.java
    ).allowMainThreadQueries().build()
}