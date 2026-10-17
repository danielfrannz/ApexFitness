package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.WorkoutDataSeed
import com.example.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("ApexFitness", appName)
    }

    @Test
    fun `verify 30 days workout plan generation`() {
        val days = WorkoutDataSeed.generate30Days()
        assertEquals(30, days.size)
        assertTrue(days.all { it.exercisesRaw.isNotBlank() })
    }

    @Test
    fun `verify user profile calorie deficit calculation`() {
        val profile = UserProfile(
            startWeightKg = 80f,
            currentWeightKg = 78f,
            targetWeightKg = 72f,
            heightCm = 175f,
            age = 25,
            isMale = true
        )
        val deficitTarget = profile.calculateRecommendedCalorieDeficit()
        assertTrue(deficitTarget in 1500..2500)
    }
}
