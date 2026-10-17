package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.WorkoutDataSeed
import com.example.data.model.CalorieLog
import com.example.data.model.UserProfile
import com.example.data.model.WeightLog
import com.example.data.model.WorkoutDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FitnessRepository(private val database: AppDatabase) {

    private val workoutDao = database.workoutDao()
    private val calorieDao = database.calorieDao()
    private val weightDao = database.weightDao()
    private val profileDao = database.userProfileDao()

    val allWorkouts: Flow<List<WorkoutDay>> = workoutDao.getAllDays()
    val completedDaysCount: Flow<Int> = workoutDao.getCompletedDaysCount()

    val allWeightLogsAsc: Flow<List<WeightLog>> = weightDao.getAllWeightLogsAsc()
    val allWeightLogsDesc: Flow<List<WeightLog>> = weightDao.getAllWeightLogsDesc()
    val latestWeight: Flow<WeightLog?> = weightDao.getLatestWeightLog()

    val userProfile: Flow<UserProfile?> = profileDao.getUserProfile()

    suspend fun checkAndSeedInitialDataIfNeeded() = withContext(Dispatchers.IO) {
        // If workouts are empty, seed them
        val testDay = workoutDao.getDayByNumber(1)
        if (testDay == null) {
            workoutDao.insertAll(WorkoutDataSeed.generate30Days())
            val profile = UserProfile(
                id = 1,
                name = "Atlet Pro",
                startWeightKg = 82.0f,
                currentWeightKg = 79.2f,
                targetWeightKg = 74.0f,
                heightCm = 175.0f,
                age = 26,
                isMale = true,
                dailyCalorieTarget = 1950,
                waterIntakeMl = 1500,
                waterTargetMl = 2500,
                isDarkMode = true
            )
            profileDao.insertOrUpdate(profile)

            val now = System.currentTimeMillis()
            val dayMs = 86400000L
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val initialWeightLogs = listOf(
                WeightLog(dateString = sdf.format(Date(now - 14 * dayMs)), weightKg = 82.0f, note = "Awal Program 30 Hari", timestamp = now - 14 * dayMs),
                WeightLog(dateString = sdf.format(Date(now - 10 * dayMs)), weightKg = 81.3f, note = "Akhir Minggu 1", timestamp = now - 10 * dayMs),
                WeightLog(dateString = sdf.format(Date(now - 7 * dayMs)), weightKg = 80.6f, note = "Defisit kalori konsisten", timestamp = now - 7 * dayMs),
                WeightLog(dateString = sdf.format(Date(now - 4 * dayMs)), weightKg = 79.9f, note = "Kaki terasa lebih ringan", timestamp = now - 4 * dayMs),
                WeightLog(dateString = sdf.format(Date(now - 1 * dayMs)), weightKg = 79.2f, note = "Evaluasi timbangan pagi", timestamp = now - 1 * dayMs)
            )
            initialWeightLogs.forEach { weightDao.insertLog(it) }

            val todayStr = sdf.format(Date(now))
            val sampleMeals = listOf(
                CalorieLog(dateString = todayStr, mealType = "BREAKFAST", foodName = "Oatmeal Pisang & Telur Rebus 2 butir", calories = 380, proteinG = 22f, carbsG = 48f, fatG = 11f, timestamp = now - 4 * 3600000L),
                CalorieLog(dateString = todayStr, mealType = "LUNCH", foodName = "Dada Ayam Panggang & Nasi Merah", calories = 520, proteinG = 42f, carbsG = 54f, fatG = 9f, timestamp = now - 2 * 3600000L),
                CalorieLog(dateString = todayStr, mealType = "SNACK", foodName = "Greek Yogurt & Almond Segenggam", calories = 210, proteinG = 15f, carbsG = 12f, fatG = 10f, timestamp = now - 3600000L)
            )
            sampleMeals.forEach { calorieDao.insertLog(it) }
        }
    }

    suspend fun setWorkoutCompleted(dayNumber: Int, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        val timestamp = if (isCompleted) System.currentTimeMillis() else null
        workoutDao.setWorkoutCompleted(dayNumber, isCompleted, timestamp)
    }

    suspend fun resetAllWorkouts() = withContext(Dispatchers.IO) {
        workoutDao.resetAllWorkouts()
    }

    fun getMealsForDate(dateString: String): Flow<List<CalorieLog>> {
        return calorieDao.getLogsForDate(dateString)
    }

    suspend fun addMeal(
        mealType: String,
        foodName: String,
        calories: Int,
        proteinG: Float,
        carbsG: Float,
        fatG: Float
    ) = withContext(Dispatchers.IO) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(Date())
        val log = CalorieLog(
            dateString = todayStr,
            mealType = mealType,
            foodName = foodName,
            calories = calories,
            proteinG = proteinG,
            carbsG = carbsG,
            fatG = fatG,
            timestamp = System.currentTimeMillis()
        )
        calorieDao.insertLog(log)
    }

    suspend fun deleteMeal(id: Long) = withContext(Dispatchers.IO) {
        calorieDao.deleteById(id)
    }

    suspend fun addWeightLog(weightKg: Float, note: String) = withContext(Dispatchers.IO) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(Date())
        val log = WeightLog(
            dateString = todayStr,
            weightKg = weightKg,
            note = note,
            timestamp = System.currentTimeMillis()
        )
        weightDao.insertLog(log)
        // Also update current weight in profile
        profileDao.updateCurrentWeight(weightKg)
    }

    suspend fun deleteWeightLog(id: Long) = withContext(Dispatchers.IO) {
        weightDao.deleteById(id)
    }

    suspend fun updateUserProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        profileDao.insertOrUpdate(profile)
    }

    suspend fun updateTargetWeight(targetKg: Float) = withContext(Dispatchers.IO) {
        profileDao.updateTargetWeight(targetKg)
    }

    suspend fun updateCalorieTarget(targetKcal: Int) = withContext(Dispatchers.IO) {
        profileDao.updateCalorieTarget(targetKcal)
    }

    suspend fun addWater(amountMl: Int) = withContext(Dispatchers.IO) {
        // Will be managed via ViewModel state and updated to profile
    }

    suspend fun updateDarkMode(isDark: Boolean) = withContext(Dispatchers.IO) {
        profileDao.updateDarkMode(isDark)
    }
}
