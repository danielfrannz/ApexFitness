package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.roundToInt

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "Atlet",
    val startWeightKg: Float = 80.0f,
    val currentWeightKg: Float = 78.5f,
    val targetWeightKg: Float = 72.0f,
    val heightCm: Float = 175.0f,
    val age: Int = 26,
    val isMale: Boolean = true,
    val dailyCalorieTarget: Int = 1950,
    val waterIntakeMl: Int = 1250,
    val waterTargetMl: Int = 2500,
    val isDarkMode: Boolean = true
) {
    val bmi: Float
        get() {
            val heightM = heightCm / 100f
            return if (heightM > 0) currentWeightKg / (heightM * heightM) else 0f
        }

    val bmiCategory: String
        get() = when {
            bmi < 18.5f -> "Underweight"
            bmi < 24.9f -> "Normal Atletis"
            bmi < 29.9f -> "Overweight"
            else -> "Obesitas"
        }

    val totalWeightToLose: Float
        get() = (startWeightKg - targetWeightKg).coerceAtLeast(0f)

    val remainingWeightToLose: Float
        get() = (currentWeightKg - targetWeightKg).coerceAtLeast(0f)

    val progressPercentage: Float
        get() {
            val total = totalWeightToLose
            if (total <= 0f) return 1f
            val lost = (startWeightKg - currentWeightKg).coerceAtLeast(0f)
            return (lost / total).coerceIn(0f, 1f)
        }

    fun calculateRecommendedCalorieDeficit(): Int {
        // Mifflin-St Jeor Equation for BMR
        val bmr = if (isMale) {
            (10 * currentWeightKg) + (6.25 * heightCm) - (5 * age) + 5
        } else {
            (10 * currentWeightKg) + (6.25 * heightCm) - (5 * age) - 161
        }
        // Active athlete multiplier ~ 1.55 (Moderate-High activity)
        val tdee = bmr * 1.55
        // Safe 500 kcal deficit for sustained fat burn ~0.5kg/week
        return (tdee - 500.0).roundToInt().coerceAtLeast(1500)
    }
}
