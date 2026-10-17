package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.WeightLog
import com.example.ui.components.WeightTrendChart
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
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
fun WeightAnalyticsScreen(
    uiState: FitnessUiState,
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val profile = uiState.userProfile
    val currentWeight = uiState.latestWeight?.weightKg ?: profile.currentWeightKg

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoidBlack)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("weight_analytics_lazy_column"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Target Transformation Dashboard Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("weight_dashboard_card"),
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
                                    text = "TARGET PENURUNAN BERAT BADAN",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = VoltLime,
                                    letterSpacing = 1.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${"%.1f".format(currentWeight)} kg → ${"%.1f".format(profile.targetWeightKg)} kg",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                            }

                            IconButton(
                                onClick = { viewModel.setShowEditTargetDialog(true) },
                                modifier = Modifier
                                    .background(TitaniumElevated, CircleShape)
                                    .size(38.dp)
                                    .testTag("edit_target_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Target",
                                    tint = CyberCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Progress Bar
                        LinearProgressIndicator(
                            progress = { profile.progressPercentage },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = VoltLime,
                            trackColor = TitaniumElevated
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Awal: ${"%.1f".format(profile.startWeightKg)} kg",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                            Text(
                                text = "${(profile.progressPercentage * 100).toInt()}% Menuju Sasaran",
                                style = MaterialTheme.typography.labelSmall,
                                color = VoltLime,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Target: ${"%.1f".format(profile.targetWeightKg)} kg",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyberCyan
                            )
                        }
                    }
                }
            }

            // Interactive Trend Chart
            item {
                WeightTrendChart(
                    logs = uiState.weightLogsAsc,
                    targetWeightKg = profile.targetWeightKg,
                    startWeightKg = profile.startWeightKg
                )
            }

            // Body Metrics Card (BMI & Speed)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // BMI Card
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Indeks Massa Tubuh (BMI)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${"%.1f".format(profile.bmi)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            Text(text = profile.bmiCategory, style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Height & Baseline Card
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.TrendingDown, contentDescription = null, tint = VoltLime, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Sisa Penurunan", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${"%.1f".format(profile.remainingWeightToLose)} kg",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = VoltLime
                            )
                            Text(text = "Tinggi: ${"%.0f".format(profile.heightCm)} cm", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }
                    }
                }
            }

            // Section Header: Riwayat Timbangan Berkala
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RIWAYAT TIMBANGAN BERKALA",
                        style = MaterialTheme.typography.labelSmall,
                        color = VoltLime,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(
                        onClick = { viewModel.setShowAddWeightDialog(true) },
                        modifier = Modifier.testTag("log_weight_text_button")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = VoltLime, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Catat Berat", color = VoltLime, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Weight History List
            if (uiState.weightLogsDesc.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = TitaniumDark)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Belum ada data riwayat timbangan.", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        }
                    }
                }
            } else {
                itemsIndexed(uiState.weightLogsDesc, key = { _, item -> item.id }) { index, log ->
                    val prevLog = uiState.weightLogsDesc.getOrNull(index + 1)
                    val delta = if (prevLog != null) log.weightKg - prevLog.weightKg else 0f

                    WeightLogItem(
                        log = log,
                        deltaKg = delta,
                        onDelete = { viewModel.deleteWeightLog(log.id) }
                    )
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { viewModel.setShowAddWeightDialog(true) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 90.dp)
                .testTag("add_weight_fab"),
            containerColor = VoltLime,
            contentColor = VoidBlack
        ) {
            Icon(imageVector = Icons.Default.Scale, contentDescription = "Catat Berat Badan")
        }

        // Add Weight Dialog
        if (uiState.showAddWeightDialog) {
            AddWeightLogDialog(
                currentWeight = currentWeight,
                onDismiss = { viewModel.setShowAddWeightDialog(false) },
                onConfirm = { weight, note -> viewModel.addWeightLog(weight, note) }
            )
        }

        // Edit Target Dialog
        if (uiState.showEditTargetDialog) {
            EditTargetWeightDialog(
                currentTarget = profile.targetWeightKg,
                onDismiss = { viewModel.setShowEditTargetDialog(false) },
                onConfirm = { newTarget, autoDeficit -> viewModel.updateTargetWeight(newTarget, autoDeficit) }
            )
        }
    }
}

@Composable
fun WeightLogItem(
    log: WeightLog,
    deltaKg: Float,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(TitaniumElevated, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Scale, contentDescription = null, tint = VoltLime, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${"%.1f".format(log.weightKg)} kg",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Text(
                    text = "${log.dateString}${if (log.note.isNotBlank()) " • ${log.note}" else ""}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            if (deltaKg != 0f) {
                val isLoss = deltaKg < 0
                Box(
                    modifier = Modifier
                        .background(
                            if (isLoss) SuccessGreen.copy(alpha = 0.15f) else BorderSubtle,
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${if (deltaKg > 0) "+" else ""}${"%.1f".format(deltaKg)} kg",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isLoss) SuccessGreen else TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus", tint = TextMuted, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun AddWeightLogDialog(
    currentWeight: Float,
    onDismiss: () -> Unit,
    onConfirm: (weight: Float, note: String) -> Unit
) {
    var weightStr by remember { mutableStateOf(currentWeight.toString()) }
    var noteStr by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = TitaniumDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth().testTag("add_weight_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "CATAT HASIL TIMBANGAN",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VoltLime
                )
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = weightStr,
                    onValueChange = { weightStr = it },
                    label = { Text("Berat Badan (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth().testTag("weight_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VoltLime,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = noteStr,
                    onValueChange = { noteStr = it },
                    label = { Text("Catatan / Waktu (opsional)") },
                    placeholder = { Text("Contoh: Timbang pagi sebelum sarapan") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberCyan,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text("Batal", color = TextSecondary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val w = weightStr.toFloatOrNull()
                            if (w != null && w > 20f && w < 300f) {
                                onConfirm(w, noteStr)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VoltLime, contentColor = VoidBlack),
                        modifier = Modifier.testTag("save_weight_button")
                    ) {
                        Text("Simpan Timbangan", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun EditTargetWeightDialog(
    currentTarget: Float,
    onDismiss: () -> Unit,
    onConfirm: (target: Float, autoDeficit: Boolean) -> Unit
) {
    var targetStr by remember { mutableStateOf(currentTarget.toString()) }
    var autoDeficit by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = TitaniumDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth().testTag("edit_target_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "ATUR TARGET PENURUNAN BB",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CyberCyan
                )
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = targetStr,
                    onValueChange = { targetStr = it },
                    label = { Text("Target Berat Badan Akhir (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth().testTag("target_weight_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberCyan,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TitaniumElevated, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Checkbox(
                        checked = autoDeficit,
                        onCheckedChange = { autoDeficit = it },
                        colors = androidx.compose.material3.CheckboxDefaults.colors(checkedColor = VoltLime)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Hitung ulang target defisit kalori harian otomatis (TDEE - 500 kcal)",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text("Batal", color = TextSecondary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val t = targetStr.toFloatOrNull()
                            if (t != null && t > 20f && t < 300f) {
                                onConfirm(t, autoDeficit)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = VoidBlack),
                        modifier = Modifier.testTag("save_target_button")
                    ) {
                        Text("Terapkan Target", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
