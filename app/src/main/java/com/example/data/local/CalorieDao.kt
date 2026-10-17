package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.CalorieLog
import kotlinx.coroutines.flow.Flow

@Dao
interface CalorieDao {
    @Query("SELECT * FROM calorie_logs WHERE dateString = :date ORDER BY timestamp DESC")
    fun getLogsForDate(date: String): Flow<List<CalorieLog>>

    @Query("SELECT SUM(calories) FROM calorie_logs WHERE dateString = :date")
    fun getTotalCaloriesForDate(date: String): Flow<Int?>

    @Query("SELECT * FROM calorie_logs ORDER BY timestamp DESC LIMIT 50")
    fun getRecentLogs(): Flow<List<CalorieLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: CalorieLog): Long

    @Delete
    suspend fun deleteLog(log: CalorieLog)

    @Query("DELETE FROM calorie_logs WHERE id = :id")
    suspend fun deleteById(id: Long)
}
