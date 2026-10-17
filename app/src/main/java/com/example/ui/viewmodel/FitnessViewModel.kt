package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.UserProfile
import com.example.data.model.WorkoutDay
import com.example.data.repository.FitnessRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FitnessViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FitnessRepository
    private val _uiState = MutableStateFlow(FitnessUiState())
    val uiState: StateFlow<FitnessUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private val todayStr: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    init {
        val db = AppDatabase.getDatabase(application)
        repository = FitnessRepository(db)

        viewModelScope.launch {
            repository.checkAndSeedInitialDataIfNeeded()
        }

        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            repository.allWorkouts.collect { workouts ->
                val todayCompletedBurn = workouts.filter {
                    it.isCompleted && it.completedTimestamp != null
                }.sumOf { it.caloriesBurnEst }
                val effectiveBurn = if (todayCompletedBurn > 0) todayCompletedBurn else 320

                _uiState.update { current ->
                    current.copy(
                        workouts = workouts,
                        todayBurnedCalories = effectiveBurn,
                        activeWorkoutDay = current.activeWorkoutDay?.let { active ->
                            workouts.find { it.dayNumber == active.dayNumber } ?: active
                        }
                    )
                }
            }
        }

        viewModelScope.launch {
            repository.completedDaysCount.collect { count ->
                _uiState.update { it.copy(completedDaysCount = count) }
            }
        }

        viewModelScope.launch {
            repository.userProfile.collect { profile ->
                if (profile != null) {
                    _uiState.update { it.copy(userProfile = profile) }
                }
            }
        }

        viewModelScope.launch {
            repository.getMealsForDate(todayStr).collect { meals ->
                _uiState.update { it.copy(todayMeals = meals) }
            }
        }

        viewModelScope.launch {
            repository.allWeightLogsAsc.collect { asc ->
                _uiState.update { it.copy(weightLogsAsc = asc) }
            }
        }

        viewModelScope.launch {
            repository.allWeightLogsDesc.collect { desc ->
                _uiState.update { it.copy(weightLogsDesc = desc, latestWeight = desc.firstOrNull()) }
            }
        }
    }

    fun setNavTab(tabIndex: Int) {
        _uiState.update { it.copy(currentNavTab = tabIndex) }
    }

    fun setPhaseFilter(phase: Int) {
        _uiState.update { it.copy(selectedPhaseFilter = phase) }
    }

    fun openWorkoutDetail(day: WorkoutDay) {
        _uiState.update {
            it.copy(
                activeWorkoutDay = day,
                isWorkoutSessionOpen = true,
                currentExerciseIndex = 0,
                isRestTimerActive = false
            )
        }
    }

    fun closeWorkoutDetail() {
        cancelTimer()
        _uiState.update {
            it.copy(
                isWorkoutSessionOpen = false,
                isRestTimerActive = false
            )
        }
    }

    fun toggleWorkoutCompletion(dayNumber: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.setWorkoutCompleted(dayNumber, isCompleted)
            triggerHapticFeedback()
            val msg = if (isCompleted) "Hari $dayNumber Selesai! Kalori terbakar ditambahkan." else "Status Hari $dayNumber diperbarui."
            showSnackbar(msg)
        }
    }

    fun resetAllProgramWorkouts() {
        viewModelScope.launch {
            repository.resetAllWorkouts()
            showSnackbar("Program 30 Hari berhasil di-reset.")
        }
    }

    fun nextExercise(totalExercises: Int) {
        cancelTimer()
        val nextIdx = _uiState.value.currentExerciseIndex + 1
        if (nextIdx < totalExercises) {
            _uiState.update { it.copy(currentExerciseIndex = nextIdx) }
            startRestTimer(30)
        } else {
            // Completed all exercises
            _uiState.value.activeWorkoutDay?.let { day ->
                toggleWorkoutCompletion(day.dayNumber, true)
            }
            closeWorkoutDetail()
        }
    }

    fun previousExercise() {
        cancelTimer()
        if (_uiState.value.currentExerciseIndex > 0) {
            _uiState.update { it.copy(currentExerciseIndex = it.currentExerciseIndex - 1, isRestTimerActive = false) }
        }
    }

    fun startRestTimer(seconds: Int = 30) {
        timerJob?.cancel()
        _uiState.update {
            it.copy(
                isRestTimerActive = true,
                timerSecondsRemaining = seconds,
                timerTotalSeconds = seconds
            )
        }
        timerJob = viewModelScope.launch {
            while (_uiState.value.timerSecondsRemaining > 0 && _uiState.value.isRestTimerActive) {
                delay(1000L)
                _uiState.update { it.copy(timerSecondsRemaining = it.timerSecondsRemaining - 1) }
            }
            if (_uiState.value.timerSecondsRemaining <= 0) {
                triggerHapticFeedback()
                _uiState.update { it.copy(isRestTimerActive = false) }
            }
        }
    }

    fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update { it.copy(isRestTimerActive = false) }
    }

    fun addMeal(
        mealType: String,
        foodName: String,
        calories: Int,
        proteinG: Float,
        carbsG: Float,
        fatG: Float
    ) {
        viewModelScope.launch {
            repository.addMeal(mealType, foodName, calories, proteinG, carbsG, fatG)
            _uiState.update { it.copy(showAddMealDialog = false) }
            showSnackbar("Menu '$foodName' (+${calories} kcal) dicatat.")
        }
    }

    fun deleteMeal(id: Long) {
        viewModelScope.launch {
            repository.deleteMeal(id)
            showSnackbar("Catatan makanan dihapus.")
        }
    }

    fun addWeightLog(weightKg: Float, note: String) {
        viewModelScope.launch {
            repository.addWeightLog(weightKg, note)
            _uiState.update { it.copy(showAddWeightDialog = false) }
            showSnackbar("Timbangan ${weightKg} kg berhasil dicatat.")
        }
    }

    fun deleteWeightLog(id: Long) {
        viewModelScope.launch {
            repository.deleteWeightLog(id)
            showSnackbar("Catatan berat badan dihapus.")
        }
    }

    fun updateTargetWeight(targetKg: Float, applyAutoDeficit: Boolean) {
        viewModelScope.launch {
            val currentProfile = _uiState.value.userProfile
            val updated = currentProfile.copy(targetWeightKg = targetKg)
            val newDeficitTarget = if (applyAutoDeficit) {
                updated.calculateRecommendedCalorieDeficit()
            } else {
                currentProfile.dailyCalorieTarget
            }
            val finalProfile = updated.copy(dailyCalorieTarget = newDeficitTarget)
            repository.updateUserProfile(finalProfile)
            _uiState.update { it.copy(showEditTargetDialog = false) }
            showSnackbar("Target berat badan ${targetKg} kg disimpan.")
        }
    }

    fun updateCalorieTarget(targetKcal: Int) {
        viewModelScope.launch {
            repository.updateCalorieTarget(targetKcal)
            showSnackbar("Target kalori harian diatur ke ${targetKcal} kcal.")
        }
    }

    fun addWaterCup() {
        viewModelScope.launch {
            val current = _uiState.value.userProfile.waterIntakeMl
            val updated = (current + 250).coerceAtMost(5000)
            val newProfile = _uiState.value.userProfile.copy(waterIntakeMl = updated)
            repository.updateUserProfile(newProfile)
            triggerHapticFeedback()
        }
    }

    fun toggleDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            repository.updateDarkMode(isDark)
        }
    }

    fun setShowAddMealDialog(show: Boolean) {
        _uiState.update { it.copy(showAddMealDialog = show) }
    }

    fun setShowAddWeightDialog(show: Boolean) {
        _uiState.update { it.copy(showAddWeightDialog = show) }
    }

    fun setShowEditTargetDialog(show: Boolean) {
        _uiState.update { it.copy(showEditTargetDialog = show) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    private fun showSnackbar(msg: String) {
        _uiState.update { it.copy(snackbarMessage = msg) }
    }

    private fun triggerHapticFeedback() {
        try {
            val app = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = app.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = app.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(50)
                }
            }
        } catch (_: Exception) {
            // Graceful fallback if device has no vibrator
        }
    }
}
