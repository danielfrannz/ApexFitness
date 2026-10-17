package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WeightLog
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TitaniumDark
import com.example.ui.theme.TitaniumElevated
import com.example.ui.theme.VoltLime

@Composable
fun WeightTrendChart(
    logs: List<WeightLog>,
    targetWeightKg: Float,
    startWeightKg: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("weight_trend_chart_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TitaniumDark),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(BorderSubtle, Color.Transparent)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TrendingDown,
                        contentDescription = "Trend",
                        tint = VoltLime,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "KURVA PROGRES TIMBANGAN",
                        style = MaterialTheme.typography.labelSmall,
                        color = VoltLime,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .background(TitaniumElevated, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Target: ${"%.1f".format(targetWeightKg)} kg",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (logs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(TitaniumElevated, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada catatan timbangan.\nCatat berat badan pertama Anda untuk melihat grafik!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                // Interactive Smooth Canvas Chart
                val weights = logs.map { it.weightKg }
                val minWeight = (weights.minOrNull() ?: targetWeightKg).coerceAtMost(targetWeightKg) - 1.5f
                val maxWeight = (weights.maxOrNull() ?: startWeightKg).coerceAtLeast(startWeightKg) + 1.5f
                val weightRange = (maxWeight - minWeight).coerceAtLeast(1f)

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .padding(horizontal = 8.dp, vertical = 10.dp)
                ) {
                    val width = size.width
                    val height = size.height
                    val bottomPadding = 24f
                    val chartHeight = height - bottomPadding

                    // Horizontal Grid Lines
                    val gridLines = 4
                    for (i in 0..gridLines) {
                        val y = chartHeight * (i.toFloat() / gridLines)
                        drawLine(
                            color = BorderSubtle.copy(alpha = 0.5f),
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1f
                        )
                    }

                    // Target Dashed Line
                    val targetY = chartHeight - ((targetWeightKg - minWeight) / weightRange * chartHeight)
                    if (targetY in 0f..chartHeight) {
                        drawLine(
                            color = CyberCyan.copy(alpha = 0.8f),
                            start = Offset(0f, targetY),
                            end = Offset(width, targetY),
                            strokeWidth = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    }

                    // Compute points
                    val points = logs.mapIndexed { index, item ->
                        val x = if (logs.size > 1) {
                            (index.toFloat() / (logs.size - 1)) * width
                        } else {
                            width / 2f
                        }
                        val y = chartHeight - ((item.weightKg - minWeight) / weightRange * chartHeight)
                        Offset(x, y.coerceIn(0f, chartHeight))
                    }

                    // Draw Smooth Cubic Curve
                    if (points.size >= 2) {
                        val strokePath = Path()
                        val fillPath = Path()

                        strokePath.moveTo(points[0].x, points[0].y)
                        fillPath.moveTo(points[0].x, chartHeight)
                        fillPath.lineTo(points[0].x, points[0].y)

                        for (i in 0 until points.size - 1) {
                            val p0 = points[i]
                            val p1 = points[i + 1]
                            val controlPoint1 = Offset(p0.x + (p1.x - p0.x) / 2f, p0.y)
                            val controlPoint2 = Offset(p0.x + (p1.x - p0.x) / 2f, p1.y)
                            strokePath.cubicTo(
                                controlPoint1.x, controlPoint1.y,
                                controlPoint2.x, controlPoint2.y,
                                p1.x, p1.y
                            )
                            fillPath.cubicTo(
                                controlPoint1.x, controlPoint1.y,
                                controlPoint2.x, controlPoint2.y,
                                p1.x, p1.y
                            )
                        }

                        fillPath.lineTo(points.last().x, chartHeight)
                        fillPath.close()

                        // Draw Gradient Fill
                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    VoltLime.copy(alpha = 0.28f),
                                    CyberCyan.copy(alpha = 0.05f),
                                    Color.Transparent
                                ),
                                startY = 0f,
                                endY = chartHeight
                            )
                        )

                        // Draw Line
                        drawPath(
                            path = strokePath,
                            brush = Brush.horizontalGradient(listOf(VoltLime, CyberCyan)),
                            style = Stroke(width = 3.dp.toPx(), pathEffect = null)
                        )
                    }

                    // Draw point dots and circles
                    points.forEachIndexed { idx, pt ->
                        val isLatest = idx == points.size - 1
                        // Halo
                        drawCircle(
                            color = if (isLatest) VoltLime.copy(alpha = 0.35f) else TitaniumElevated,
                            radius = if (isLatest) 8.dp.toPx() else 5.dp.toPx(),
                            center = pt
                        )
                        // Inner dot
                        drawCircle(
                            color = if (isLatest) VoltLime else CyberCyan,
                            radius = if (isLatest) 4.5f.dp.toPx() else 3.dp.toPx(),
                            center = pt
                        )
                    }
                }

                // X-axis date range & current stats
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = logs.firstOrNull()?.dateString ?: "-",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Text(
                        text = "Garis Putus: Target",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberCyan.copy(alpha = 0.8f)
                    )
                    Text(
                        text = logs.lastOrNull()?.dateString ?: "-",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Summary Metric Bar
            val latest = logs.lastOrNull()?.weightKg ?: startWeightKg
            val totalLost = (startWeightKg - latest).coerceAtLeast(0f)
            val remaining = (latest - targetWeightKg).coerceAtLeast(0f)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TitaniumElevated, RoundedCornerShape(10.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Timbangan Kini", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Text(
                        text = "${"%.1f".format(latest)} kg",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 1.dp, height = 28.dp)
                        .background(BorderSubtle)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Total Turun", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Text(
                        text = "-${"%.1f".format(totalLost)} kg",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VoltLime
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 1.dp, height = 28.dp)
                        .background(BorderSubtle)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Sisa Target", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Text(
                        text = "${"%.1f".format(remaining)} kg",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyberCyan
                    )
                }
            }
        }
    }
}
