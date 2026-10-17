package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfile)

    @Update
    suspend fun update(profile: UserProfile)

    @Query("UPDATE user_profile SET currentWeightKg = :weight WHERE id = 1")
    suspend fun updateCurrentWeight(weight: Float)

    @Query("UPDATE user_profile SET targetWeightKg = :target WHERE id = 1")
    suspend fun updateTargetWeight(target: Float)

    @Query("UPDATE user_profile SET dailyCalorieTarget = :target WHERE id = 1")
    suspend fun updateCalorieTarget(target: Int)

    @Query("UPDATE user_profile SET waterIntakeMl = :amount WHERE id = 1")
    suspend fun updateWaterIntake(amount: Int)

    @Query("UPDATE user_profile SET isDarkMode = :isDark WHERE id = 1")
    suspend fun updateDarkMode(isDark: Boolean)
}
