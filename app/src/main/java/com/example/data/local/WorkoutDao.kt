package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.WorkoutDay
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout_days ORDER BY dayNumber ASC")
    fun getAllDays(): Flow<List<WorkoutDay>>

    @Query("SELECT * FROM workout_days WHERE dayNumber = :dayNumber")
    suspend fun getDayByNumber(dayNumber: Int): WorkoutDay?

    @Query("SELECT COUNT(*) FROM workout_days WHERE isCompleted = 1")
    fun getCompletedDaysCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(days: List<WorkoutDay>)

    @Update
    suspend fun updateDay(day: WorkoutDay)

    @Query("UPDATE workout_days SET isCompleted = :completed, completedTimestamp = :timestamp WHERE dayNumber = :dayNumber")
    suspend fun setWorkoutCompleted(dayNumber: Int, completed: Boolean, timestamp: Long?)

    @Query("UPDATE workout_days SET isCompleted = 0, completedTimestamp = NULL")
    suspend fun resetAllWorkouts()
}
