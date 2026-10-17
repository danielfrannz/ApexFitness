package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.WorkoutDay
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TitaniumDark
import com.example.ui.theme.TitaniumElevated
import com.example.ui.theme.VoidBlack
import com.example.ui.theme.VoltLime
import com.example.ui.viewmodel.FitnessUiState
import com.example.ui.viewmodel.FitnessViewModel

@Composable
fun WorkoutPlanScreen(
    uiState: FitnessUiState,
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val filteredWorkouts = when (uiState.selectedPhaseFilter) {
        1 -> uiState.workouts.filter { it.phaseNumber == 1 }
        2 -> uiState.workouts.filter { it.phaseNumber == 2 }
        3 -> uiState.workouts.filter { it.phaseNumber == 3 }
        4 -> uiState.workouts.filter { it.phaseNumber == 4 }
        else -> uiState.workouts
    }

    val totalWorkouts = 30
    val completedCount = uiState.completedDaysCount
    val progressFraction = (completedCount.toFloat() / totalWorkouts.toFloat()).coerceIn(0f, 1f)

    Box(modifier = modifier.fillMaxSize().background(VoidBlack)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("workout_list_column"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Progress Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("program_progress_header"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "PROGRAM SHRED 30 HARI",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = VoltLime,
                                    letterSpacing = 1.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$completedCount dari 30 Hari Selesai",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(TitaniumElevated, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${(progressFraction * 100).toInt()}%",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = VoltLime
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = VoltLime,
                            trackColor = TitaniumElevated
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Bakar Kalori Tertarget & Tonus Otot",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                            Text(
                                text = "${uiState.todayBurnedCalories} kcal terbakar hari ini",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElectricOrange,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Phase Filter Chips
            item {
                val phases = listOf(
                    0 to "Semua Hari (30)",
                    1 to "Fase 1 (1-7)",
                    2 to "Fase 2 (8-14)",
                    3 to "Fase 3 (15-21)",
                    4 to "Fase 4 (22-30)"
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.testTag("phase_chips_row")
                ) {
                    items(phases) { (phaseId, label) ->
                        val isSelected = uiState.selectedPhaseFilter == phaseId
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setPhaseFilter(phaseId) },
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = TitaniumDark,
                                labelColor = TextSecondary,
                                selectedContainerColor = VoltLime,
                                selectedLabelColor = VoidBlack
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) VoltLime else BorderSubtle
                            )
                        )
                    }
                }
            }

            // List of Workout Days
            items(filteredWorkouts, key = { it.dayNumber }) { day ->
                DayWorkoutCard(
                    day = day,
                    onClick = { viewModel.openWorkoutDetail(day) },
                    onToggleComplete = { isDone -> viewModel.toggleWorkoutCompletion(day.dayNumber, isDone) }
                )
            }
        }

        // Active Workout Modal / Dialog
        if (uiState.isWorkoutSessionOpen && uiState.activeWorkoutDay != null) {
            ActiveWorkoutDialog(
                day = uiState.activeWorkoutDay,
                currentExerciseIndex = uiState.currentExerciseIndex,
                isRestTimerActive = uiState.isRestTimerActive,
                timerSeconds = uiState.timerSecondsRemaining,
                timerTotalSeconds = uiState.timerTotalSeconds,
                onClose = { viewModel.closeWorkoutDetail() },
                onNextExercise = { total -> viewModel.nextExercise(total) },
                onPrevExercise = { viewModel.previousExercise() },
                onStartRestTimer = { viewModel.startRestTimer(30) },
                onCancelTimer = { viewModel.cancelTimer() },
                onCompleteDay = {
                    viewModel.toggleWorkoutCompletion(uiState.activeWorkoutDay.dayNumber, true)
                    viewModel.closeWorkoutDetail()
                }
            )
        }
    }
}

@Composable
fun DayWorkoutCard(
    day: WorkoutDay,
    onClick: () -> Unit,
    onToggleComplete: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("day_card_${day.dayNumber}")
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (day.isCompleted) TitaniumElevated.copy(alpha = 0.7f) else TitaniumDark
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.verticalGradient(
                listOf(
                    if (day.isCompleted) SuccessGreen.copy(alpha = 0.6f) else BorderSubtle,
                    BorderSubtle.copy(alpha = 0.3f)
                )
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Day Number Badge
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        if (day.isCompleted) SuccessGreen.copy(alpha = 0.2f) else TitaniumElevated,
                        RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (day.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selesai",
                        tint = SuccessGreen,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "HARI",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "${day.dayNumber}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = if (day.isRestDay) CyberCyan else VoltLime
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = day.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (day.isCompleted) TextSecondary else TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = day.focusDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Waktu",
                            tint = TextSecondary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${day.durationMinutes} mnt",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Kalori",
                            tint = ElectricOrange,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "~${day.caloriesBurnEst} kcal",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (day.isRestDay) {
                        Box(
                            modifier = Modifier
                                .background(CyberCyan.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "REST & MOBILITY",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 8.sp,
                                color = CyberCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Quick Toggle Complete Action
            IconButton(
                onClick = { onToggleComplete(!day.isCompleted) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("toggle_complete_${day.dayNumber}")
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            if (day.isCompleted) SuccessGreen else TitaniumElevated,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (day.isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selesai",
                            tint = VoidBlack,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Mulai",
                            tint = VoltLime,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveWorkoutDialog(
    day: WorkoutDay,
    currentExerciseIndex: Int,
    isRestTimerActive: Boolean,
    timerSeconds: Int,
    timerTotalSeconds: Int,
    onClose: () -> Unit,
    onNextExercise: (Int) -> Unit,
    onPrevExercise: () -> Unit,
    onStartRestTimer: () -> Unit,
    onCancelTimer: () -> Unit,
    onCompleteDay: () -> Unit
) {
    val exercises = day.parseExercises()
    val totalEx = exercises.size
    val currentEx = if (exercises.isNotEmpty()) exercises[currentExerciseIndex.coerceIn(0, totalEx - 1)] else null

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("active_workout_fullscreen_dialog"),
            color = VoidBlack
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "HARI ${day.dayNumber} • ${day.phaseName.uppercase()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VoltLime,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = day.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    }
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("close_workout_dialog")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup", tint = TextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Step Indicator
                if (totalEx > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        for (i in 0 until totalEx) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .background(
                                        when {
                                            i < currentExerciseIndex -> VoltLime
                                            i == currentExerciseIndex -> CyberCyan
                                            else -> TitaniumElevated
                                        },
                                        RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Gerakan ${currentExerciseIndex + 1} dari $totalEx",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Exercise Detail Card
                if (currentEx != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Target muscle badge
                            Box(
                                modifier = Modifier
                                    .background(TitaniumElevated, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (day.isRestDay) Icons.Default.SelfImprovement else Icons.Default.FitnessCenter,
                                        contentDescription = null,
                                        tint = CyberCyan,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = currentEx.targetMuscle,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyberCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = currentEx.name,
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Sets and Reps Athletic Badge
                            Row(
                                modifier = Modifier
                                    .background(TitaniumElevated, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "SET", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                    Text(text = "${currentEx.sets}x", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = VoltLime)
                                }
                                Box(modifier = Modifier.size(1.dp, 30.dp).background(BorderSubtle))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "TARGET", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                    Text(text = currentEx.repsOrTime, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Coach Tips
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = TitaniumElevated)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "PETUNJUK ATLET:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = VoltLime,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = currentEx.tips,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Rest Timer Section
                            AnimatedVisibility(visible = isRestTimerActive) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "ISTIRAHAT SEBELUM SET BERIKUTNYA",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyberCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
                                        val timerRatio = if (timerTotalSeconds > 0) timerSeconds.toFloat() / timerTotalSeconds.toFloat() else 0f
                                        CircularProgressIndicator(
                                            progress = { timerRatio },
                                            modifier = Modifier.size(80.dp),
                                            color = VoltLime,
                                            trackColor = TitaniumElevated,
                                            strokeWidth = 6.dp
                                        )
                                        Text(
                                            text = "${timerSeconds}s",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Black,
                                            color = TextPrimary
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = onCancelTimer,
                                        colors = ButtonDefaults.buttonColors(containerColor = TitaniumElevated)
                                    ) {
                                        Text("Lewati Istirahat", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }

                            if (!isRestTimerActive) {
                                OutlinedButton(
                                    onClick = onStartRestTimer,
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberCyan)
                                ) {
                                    Icon(imageVector = Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Mulai Timer Istirahat (30s)", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Navigation Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (currentExerciseIndex > 0) {
                        OutlinedButton(
                            onClick = onPrevExercise,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Sebelumnya", color = TextPrimary)
                        }
                    }

                    Button(
                        onClick = {
                            if (currentExerciseIndex + 1 < totalEx) {
                                onNextExercise(totalEx)
                            } else {
                                onCompleteDay()
                            }
                        },
                        modifier = Modifier
                            .weight(2f)
                            .height(50.dp)
                            .testTag("next_exercise_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentExerciseIndex + 1 >= totalEx) SuccessGreen else VoltLime,
                            contentColor = VoidBlack
                        )
                    ) {
                        Text(
                            text = if (currentExerciseIndex + 1 >= totalEx) "SELESAIKAN HARI INI" else "LANJUT KE GERAKAN BERIKUTNYA",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
