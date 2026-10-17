package com.example.ui.viewmodel

import com.example.data.model.CalorieLog
import com.example.data.model.UserProfile
import com.example.data.model.WeightLog
import com.example.data.model.WorkoutDay

data class FitnessUiState(
    val workouts: List<WorkoutDay> = emptyList(),
    val completedDaysCount: Int = 0,
    val selectedPhaseFilter: Int = 0, // 0: All, 1: Fase 1, 2: Fase 2, 3: Fase 3, 4: Fase 4
    val activeWorkoutDay: WorkoutDay? = null,
    val isWorkoutSessionOpen: Boolean = false,
    val isRestTimerActive: Boolean = false,
    val timerSecondsRemaining: Int = 30,
    val timerTotalSeconds: Int = 30,
    val currentExerciseIndex: Int = 0,

    val todayMeals: List<CalorieLog> = emptyList(),
    val todayBurnedCalories: Int = 380,

    val weightLogsAsc: List<WeightLog> = emptyList(),
    val weightLogsDesc: List<WeightLog> = emptyList(),
    val latestWeight: WeightLog? = null,

    val userProfile: UserProfile = UserProfile(),
    val currentNavTab: Int = 0, // 0: Workouts, 1: Calorie, 2: Weight Chart, 3: Profile

    val showAddMealDialog: Boolean = false,
    val showAddWeightDialog: Boolean = false,
    val showEditTargetDialog: Boolean = false,
    val snackbarMessage: String? = null
) {
    val todayCalorieIntake: Int
        get() = todayMeals.sumOf { it.calories }

    val netCalories: Int
        get() = todayCalorieIntake - todayBurnedCalories

    val remainingCalorieBudget: Int
        get() = userProfile.dailyCalorieTarget - netCalories

    val totalProteinG: Float
        get() = todayMeals.sumOf { it.proteinG.toDouble() }.toFloat()

    val totalCarbsG: Float
        get() = todayMeals.sumOf { it.carbsG.toDouble() }.toFloat()

    val totalFatG: Float
        get() = todayMeals.sumOf { it.fatG.toDouble() }.toFloat()
}
