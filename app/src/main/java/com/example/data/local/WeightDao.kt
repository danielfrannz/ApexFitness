package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.WeightLog
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight_logs ORDER BY timestamp ASC")
    fun getAllWeightLogsAsc(): Flow<List<WeightLog>>

    @Query("SELECT * FROM weight_logs ORDER BY timestamp DESC")
    fun getAllWeightLogsDesc(): Flow<List<WeightLog>>

    @Query("SELECT * FROM weight_logs ORDER BY timestamp DESC LIMIT 1")
    fun getLatestWeightLog(): Flow<WeightLog?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: WeightLog): Long

    @Delete
    suspend fun deleteLog(log: WeightLog)

    @Query("DELETE FROM weight_logs WHERE id = :id")
    suspend fun deleteById(id: Long)
}
