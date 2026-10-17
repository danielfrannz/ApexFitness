package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_days")
data class WorkoutDay(
    @PrimaryKey
    val dayNumber: Int,
    val phaseNumber: Int,
    val phaseName: String,
    val title: String,
    val focusDescription: String,
    val durationMinutes: Int,
    val caloriesBurnEst: Int,
    val isRestDay: Boolean = false,
    val isCompleted: Boolean = false,
    val completedTimestamp: Long? = null,
    val exercisesRaw: String = "" // Serialized exercises separated by delimiter
) {
    fun parseExercises(): List<Exercise> {
        if (exercisesRaw.isBlank()) return emptyList()
        return exercisesRaw.split(";;").mapNotNull { item ->
            val parts = item.split("|")
            if (parts.size >= 5) {
                Exercise(
                    name = parts[0],
                    sets = parts[1].toIntOrNull() ?: 3,
                    repsOrTime = parts[2],
                    targetMuscle = parts[3],
                    tips = parts[4]
                )
            } else null
        }
    }
}
