package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.CalorieLog
import com.example.data.model.MealType
import com.example.ui.components.CalorieRingGauge
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricOrange
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
fun CalorieNutritionScreen(
    uiState: FitnessUiState,
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoidBlack)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("nutrition_lazy_column"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Main Calorie Ring Gauge
            item {
                CalorieRingGauge(
                    targetKcal = uiState.userProfile.dailyCalorieTarget,
                    intakeKcal = uiState.todayCalorieIntake,
                    burnedKcal = uiState.todayBurnedCalories
                )
            }

            // Macro Nutrients Summary Cards
            item {
                MacroNutritionBar(
                    proteinG = uiState.totalProteinG,
                    carbsG = uiState.totalCarbsG,
                    fatG = uiState.totalFatG
                )
            }

            // Water Tracker Card
            item {
                WaterHydrationCard(
                    currentMl = uiState.userProfile.waterIntakeMl,
                    targetMl = uiState.userProfile.waterTargetMl,
                    onAddWater = { viewModel.addWaterCup() }
                )
            }

            // Section Header: Catatan Makanan Hari Ini
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CATATAN MENU HARI INI",
                        style = MaterialTheme.typography.labelSmall,
                        color = VoltLime,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(
                        onClick = { viewModel.setShowAddMealDialog(true) },
                        modifier = Modifier.testTag("add_meal_text_button")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = VoltLime, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Tambah Menu", color = VoltLime, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (uiState.todayMeals.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = TitaniumDark)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.Restaurant, contentDescription = null, tint = TextMuted, modifier = Modifier.size(36.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Belum ada menu makanan tercatat hari ini.", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                Text("Klik tombol di bawah untuk mencatat sarapan atau makan siang Anda.", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            }
                        }
                    }
                }
            } else {
                items(uiState.todayMeals, key = { it.id }) { meal ->
                    MealItemCard(
                        meal = meal,
                        onDelete = { viewModel.deleteMeal(meal.id) }
                    )
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { viewModel.setShowAddMealDialog(true) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 90.dp)
                .testTag("add_meal_fab"),
            containerColor = VoltLime,
            contentColor = VoidBlack
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Makanan")
        }

        // Add Meal Dialog
        if (uiState.showAddMealDialog) {
            AddMealDialog(
                onDismiss = { viewModel.setShowAddMealDialog(false) },
                onConfirm = { type, name, cal, p, c, f ->
                    viewModel.addMeal(type, name, cal, p, c, f)
                }
            )
        }
    }
}

@Composable
fun MacroNutritionBar(
    proteinG: Float,
    carbsG: Float,
    fatG: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "DISTRIBUSI MAKRONUTRISI",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Protein
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumElevated)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Protein", style = MaterialTheme.typography.labelSmall, color = CyberCyan)
                        Text(text = "${"%.1f".format(proteinG)}g", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = TextPrimary)
                    }
                }
                // Carbs
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumElevated)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Karbohidrat", style = MaterialTheme.typography.labelSmall, color = VoltLime)
                        Text(text = "${"%.1f".format(carbsG)}g", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = TextPrimary)
                    }
                }
                // Fat
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = TitaniumElevated)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Lemak", style = MaterialTheme.typography.labelSmall, color = ElectricOrange)
                        Text(text = "${"%.1f".format(fatG)}g", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = TextPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun WaterHydrationCard(
    currentMl: Int,
    targetMl: Int,
    onAddWater: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = (currentMl.toFloat() / targetMl.toFloat()).coerceIn(0f, 1f)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(CyberCyan.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.LocalDrink, contentDescription = "Air", tint = CyberCyan, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Hidrasi Atlet", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "$currentMl / $targetMl ml", style = MaterialTheme.typography.labelSmall, color = CyberCyan, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = CyberCyan,
                    trackColor = TitaniumElevated
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onAddWater,
                colors = ButtonDefaults.buttonColors(containerColor = TitaniumElevated, contentColor = CyberCyan),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(text = "+250ml", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MealItemCard(
    meal: CalorieLog,
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
            Column(modifier = Modifier.weight(1f)) {
                val typeLabel = when (meal.mealType) {
                    "BREAKFAST" -> "Sarapan"
                    "LUNCH" -> "Makan Siang"
                    "DINNER" -> "Makan Malam"
                    else -> "Camilan Sehat"
                }
                Text(
                    text = typeLabel.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    color = CyberCyan,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = meal.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = "P: ${"%.0f".format(meal.proteinG)}g • K: ${"%.0f".format(meal.carbsG)}g • L: ${"%.0f".format(meal.fatG)}g",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
            }

            Text(
                text = "+${meal.calories} kcal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = VoltLime
            )

            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus", tint = TextMuted, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun AddMealDialog(
    onDismiss: () -> Unit,
    onConfirm: (type: String, name: String, cal: Int, p: Float, c: Float, f: Float) -> Unit
) {
    var selectedType by remember { mutableStateOf("BREAKFAST") }
    var foodName by remember { mutableStateOf("") }
    var caloriesStr by remember { mutableStateOf("") }
    var proteinStr by remember { mutableStateOf("") }
    var carbsStr by remember { mutableStateOf("") }
    var fatStr by remember { mutableStateOf("") }

    // Quick presets
    val presets = listOf(
        Triple("Dada Ayam Panggang 150g", 240, Triple(46f, 0f, 4f)),
        Triple("Oatmeal Pisang & Madu", 310, Triple(9f, 62f, 4f)),
        Triple("Telur Rebus (2 butir)", 155, Triple(13f, 1f, 11f)),
        Triple("Nasi Merah 1 porsi", 180, Triple(4f, 38f, 2f)),
        Triple("Whey Protein Shake", 130, Triple(25f, 3f, 2f)),
        Triple("Salad Sayur & Zaitun", 120, Triple(3f, 8f, 9f))
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = TitaniumDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth().testTag("add_meal_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "CATAT MENU MAKANAN",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VoltLime
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Meal Type selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    MealType.values().forEach { type ->
                        val isSel = selectedType == type.name
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(if (isSel) VoltLime else TitaniumElevated, RoundedCornerShape(8.dp))
                                .padding(vertical = 6.dp)
                                .clickable { selectedType = type.name },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when(type) {
                                    MealType.BREAKFAST -> "Pagi"
                                    MealType.LUNCH -> "Siang"
                                    MealType.DINNER -> "Malam"
                                    MealType.SNACK -> "Snack"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) VoidBlack else TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Presets
                Text(text = "Rekomendasi Cepat Atlet:", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                Spacer(modifier = Modifier.height(6.dp))
                LazyColumn(modifier = Modifier.height(100.dp)) {
                    items(presets) { (pName, pCal, pMacros) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .background(TitaniumElevated, RoundedCornerShape(6.dp))
                                .clickable {
                                    foodName = pName
                                    caloriesStr = pCal.toString()
                                    proteinStr = pMacros.first.toString()
                                    carbsStr = pMacros.second.toString()
                                    fatStr = pMacros.third.toString()
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = pName, style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                                Text(text = "$pCal kcal", style = MaterialTheme.typography.labelSmall, color = VoltLime, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Nama Makanan / Minuman") },
                    modifier = Modifier.fillMaxWidth().testTag("food_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VoltLime,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = caloriesStr,
                        onValueChange = { caloriesStr = it },
                        label = { Text("Kalori (kcal)") },
                        modifier = Modifier.weight(1f).testTag("food_calories_input"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VoltLime,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = proteinStr,
                        onValueChange = { proteinStr = it },
                        label = { Text("Protein (g)") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberCyan,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
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
                            val cal = caloriesStr.toIntOrNull() ?: 0
                            val p = proteinStr.toFloatOrNull() ?: 0f
                            val c = carbsStr.toFloatOrNull() ?: 0f
                            val f = fatStr.toFloatOrNull() ?: 0f
                            if (foodName.isNotBlank() && cal > 0) {
                                onConfirm(selectedType, foodName, cal, p, c, f)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VoltLime, contentColor = VoidBlack),
                        modifier = Modifier.testTag("save_meal_button")
                    ) {
                        Text("Simpan Menu", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
