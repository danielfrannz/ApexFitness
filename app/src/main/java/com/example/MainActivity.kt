package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.CalorieNutritionScreen
import com.example.ui.screens.ProfileSettingsScreen
import com.example.ui.screens.WeightAnalyticsScreen
import com.example.ui.screens.WorkoutPlanScreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricOrange
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TitaniumDark
import com.example.ui.theme.TitaniumElevated
import com.example.ui.theme.VoidBlack
import com.example.ui.theme.VoltLime
import com.example.ui.viewmodel.FitnessViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FitnessViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            MyApplicationTheme(darkTheme = uiState.userProfile.isDarkMode) {
                ApexFitnessApp(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ApexFitnessApp(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle snackbar notifications
    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearSnackbar()
        }
    }

    // Predictive back navigation handling
    BackHandler(enabled = uiState.isWorkoutSessionOpen || uiState.currentNavTab != 0) {
        if (uiState.isWorkoutSessionOpen) {
            viewModel.closeWorkoutDetail()
        } else if (uiState.currentNavTab != 0) {
            viewModel.setNavTab(0)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("apex_fitness_scaffold"),
        containerColor = VoidBlack,
        topBar = {
            ApexTopBar(
                completedDays = uiState.completedDaysCount,
                burnedToday = uiState.todayBurnedCalories
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = uiState.currentNavTab,
                onTabSelected = { viewModel.setNavTab(it) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = uiState.currentNavTab,
                animationSpec = tween(durationMillis = 220),
                label = "nav_screen_crossfade"
            ) { tab ->
                when (tab) {
                    0 -> WorkoutPlanScreen(uiState = uiState, viewModel = viewModel)
                    1 -> CalorieNutritionScreen(uiState = uiState, viewModel = viewModel)
                    2 -> WeightAnalyticsScreen(uiState = uiState, viewModel = viewModel)
                    3 -> ProfileSettingsScreen(uiState = uiState, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ApexTopBar(
    completedDays: Int,
    burnedToday: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TitaniumDark)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 18.dp, vertical = 12.dp)
            .testTag("apex_top_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Brand Logo & Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(VoltLime, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Apex Shield",
                        tint = VoidBlack,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "APEX",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "FITNESS",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = VoltLime
                        )
                    }
                    Text(
                        text = "30-DAY ATHLETE PROTOCOL",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 8.sp,
                        letterSpacing = 1.5.sp,
                        color = TextMuted,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Top Quick Badges
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Days completed badge
                Box(
                    modifier = Modifier
                        .background(TitaniumElevated, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$completedDays/30 HARI",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = VoltLime,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Calories burned today badge
                Box(
                    modifier = Modifier
                        .background(TitaniumElevated, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = ElectricOrange,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${burnedToday}k",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            color = ElectricOrange,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
