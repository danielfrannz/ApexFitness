package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MealType(val labelId: String) {
    BREAKFAST("Sarapan"),
    LUNCH("Makan Siang"),
    DINNER("Makan Malam"),
    SNACK("Camilan Sehat")
}

@Entity(tableName = "calorie_logs")
data class CalorieLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateString: String, // Format: YYYY-MM-DD
    val mealType: String,
    val foodName: String,
    val calories: Int,
    val proteinG: Float = 0f,
    val carbsG: Float = 0f,
    val fatG: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)
