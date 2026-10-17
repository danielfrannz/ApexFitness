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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.BurnRed
import com.example.ui.theme.CyberCyan
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
fun ProfileSettingsScreen(
    uiState: FitnessUiState,
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val profile = uiState.userProfile
    var showResetDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoidBlack)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("profile_settings_lazy_column"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Athlete Profile Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("athlete_profile_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(TitaniumElevated, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = VoltLime, modifier = Modifier.size(32.dp))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(VoltLime.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = "PRO ATLET", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = VoltLime, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "BMI ${"%.1f".format(profile.bmi)} • ${profile.bmiCategory}",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyberCyan
                            )
                            Text(
                                text = "Tinggi: ${"%.0f".format(profile.heightCm)} cm • Usia: ${profile.age} thn",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            // Dark Mode Setting Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(TitaniumElevated, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.DarkMode, contentDescription = null, tint = VoltLime, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Mode Gelap Minimalis",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Menghemat baterai OLED & nyaman saat malam",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }
                        }

                        Switch(
                            checked = profile.isDarkMode,
                            onCheckedChange = { viewModel.toggleDarkMode(it) },
                            modifier = Modifier.testTag("dark_mode_switch"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = VoidBlack,
                                checkedTrackColor = VoltLime,
                                uncheckedThumbColor = TextMuted,
                                uncheckedTrackColor = TitaniumElevated
                            )
                        )
                    }
                }
            }

            // Calorie Target Fine Tuning Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "TARGET KALORI HARIAN AKTIF",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyberCyan,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${profile.dailyCalorieTarget} kcal",
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Rekomendasi defisit pembakaran 0.5 kg/minggu",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMuted
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { viewModel.updateCalorieTarget((profile.dailyCalorieTarget - 50).coerceAtLeast(1200)) },
                                    colors = ButtonDefaults.buttonColors(containerColor = TitaniumElevated, contentColor = TextPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("-50", fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    onClick = { viewModel.updateCalorieTarget(profile.dailyCalorieTarget + 50) },
                                    colors = ButtonDefaults.buttonColors(containerColor = TitaniumElevated, contentColor = TextPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("+50", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // Athlete Motivation & Nutrition Principles Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = VoltLime, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "3 PILAR SUKSES SHRED ATLET",
                                style = MaterialTheme.typography.labelSmall,
                                color = VoltLime,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val tips = listOf(
                            "1. Konsistensi 30 Hari: Jangan lewatkan latihan harian. Jika lelah, lakukan sesi peregangan di Hari Pemulihan.",
                            "2. Defisit Kalori Presisi: Pastikan asupan netto berada di bawah target kalori agar tubuh membakar cadangan lemak.",
                            "3. Hidrasi & Tidur 7-8 Jam: Metabolisme optimal dan pemulihan serat otot terjadi maksimal saat istirahat malam."
                        )

                        tips.forEach { tip ->
                            Text(
                                text = tip,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            // Reset Program Action Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumDark),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Mulai Ulang Program 30 Hari",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Reset status selesai semua hari ke hari 1",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }

                        OutlinedButton(
                            onClick = { showResetDialog = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BurnRed),
                            modifier = Modifier.testTag("reset_program_button")
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reset", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        // Reset Confirmation Dialog
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("Reset Program 30 Hari?", color = TextPrimary) },
                text = { Text("Apakah Anda yakin ingin mengatur ulang tanda centang selesai pada seluruh 30 hari latihan? Catatan timbangan dan kalori Anda akan tetap tersimpan.", color = TextSecondary) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.resetAllProgramWorkouts()
                            showResetDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BurnRed, contentColor = Color.White)
                    ) {
                        Text("Ya, Reset Program")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("Batal", color = TextSecondary)
                    }
                },
                containerColor = TitaniumDark
            )
        }
    }
}
