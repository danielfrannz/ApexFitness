package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.CalorieLog
import com.example.data.model.UserProfile
import com.example.data.model.WeightLog
import com.example.data.model.WorkoutDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Database(
    entities = [
        WorkoutDay::class,
        CalorieLog::class,
        WeightLog::class,
        UserProfile::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun calorieDao(): CalorieDao
    abstract fun weightDao(): WeightDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "apex_fitness.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed initial data in background coroutine
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                database.workoutDao().insertAll(WorkoutDataSeed.generate30Days())

                                val defaultProfile = UserProfile(
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
                                database.userProfileDao().insertOrUpdate(defaultProfile)

                                // Seed realistic initial weight trend logs for motivation
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
                                initialWeightLogs.forEach { database.weightDao().insertLog(it) }

                                // Seed sample daily meals for today
                                val todayStr = sdf.format(Date(now))
                                val sampleMeals = listOf(
                                    CalorieLog(dateString = todayStr, mealType = "BREAKFAST", foodName = "Oatmeal Pisang & Telur Rebus 2 butir", calories = 380, proteinG = 22f, carbsG = 48f, fatG = 11f, timestamp = now - 4 * 3600000L),
                                    CalorieLog(dateString = todayStr, mealType = "LUNCH", foodName = "Dada Ayam Panggang & Nasi Merah", calories = 520, proteinG = 42f, carbsG = 54f, fatG = 9f, timestamp = now - 2 * 3600000L),
                                    CalorieLog(dateString = todayStr, mealType = "SNACK", foodName = "Greek Yogurt & Almond Segenggam", calories = 210, proteinG = 15f, carbsG = 12f, fatG = 10f, timestamp = now - 3600000L)
                                )
                                sampleMeals.forEach { database.calorieDao().insertLog(it) }
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
