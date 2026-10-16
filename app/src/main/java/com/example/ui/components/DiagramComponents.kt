package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.*

@Composable
fun PhysicsDiagramView(
    diagramId: String,
    caption: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by androidx.compose.runtime.remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔬 संवादात्मक भौतिकी आरेख (Interactive Diagram)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(
                    onClick = { isExpanded = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ZoomIn,
                        contentDescription = "Zoom Diagram",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color(0xFF071322), RoundedCornerShape(12.dp))
                    .clickable { isExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                RenderDiagramCanvas(diagramId = diagramId)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = caption,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "टैप करके बड़ा करें और ज़ूम करें (Tap to Zoom & Inspect)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    if (isExpanded) {
        Dialog(onDismissRequest = { isExpanded = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF071322)
            ) {
                var scale by remember { mutableFloatStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }
                val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                    scale = (scale * zoomChange).coerceIn(0.8f, 4.0f)
                    offset += offsetChange
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "विस्तृत आरेख दर्शन (Zoom Mode)",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row {
                            IconButton(onClick = { scale = 1f; offset = Offset.Zero }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reset Zoom", tint = Color.White)
                            }
                            IconButton(onClick = { isExpanded = false }) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .transformable(state = state)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offset.x,
                                translationY = offset.y
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        RenderDiagramCanvas(diagramId = diagramId)
                    }

                    Text(
                        text = caption,
                        color = Color(0xFF94A3B8),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RenderDiagramCanvas(diagramId: String) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        when (diagramId) {
            "diagram_point_charges" -> drawPointCharges(this)
            "diagram_coulomb_force" -> drawCoulombForce(this)
            "diagram_field_lines" -> drawFieldLinesInteraction(this)
            "diagram_dipole" -> drawElectricDipole(this)
            "diagram_dipole_field" -> drawDipoleInUniformField(this)
            "diagram_gauss_law", "diagram_flux" -> drawGaussianSurfaceFlux(this)
            else -> drawPointCharges(this)
        }
    }
}

// 1. Positive and Negative Point Charges with accurate radial field vectors
private fun drawPointCharges(scope: DrawScope) {
    val width = scope.size.width
    val height = scope.size.height
    val cy = height / 2f
    val r = 24f

    // Positive charge at x = width * 0.3
    val cx1 = width * 0.28f
    scope.drawCircle(color = Color(0xFFEF4444), radius = r, center = Offset(cx1, cy))
    // Plus sign
    scope.drawLine(Color.White, Offset(cx1 - 10f, cy), Offset(cx1 + 10f, cy), strokeWidth = 3f)
    scope.drawLine(Color.White, Offset(cx1, cy - 10f), Offset(cx1, cy + 10f), strokeWidth = 3f)

    // Field lines radiating outwards from +q
    val angles = listOf(0.0, 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0)
    for (deg in angles) {
        val rad = Math.toRadians(deg)
        val startX = cx1 + (r + 4f) * cos(rad).toFloat()
        val startY = cy + (r + 4f) * sin(rad).toFloat()
        val endX = cx1 + (r + 46f) * cos(rad).toFloat()
        val endY = cy + (r + 46f) * sin(rad).toFloat()
        scope.drawLine(Color(0xFF38BDF8), Offset(startX, startY), Offset(endX, endY), strokeWidth = 2.5f)
        drawArrowHead(scope, Offset(startX, startY), Offset(endX, endY), Color(0xFF38BDF8))
    }

    // Negative charge at x = width * 0.72
    val cx2 = width * 0.72f
    scope.drawCircle(color = Color(0xFF3B82F6), radius = r, center = Offset(cx2, cy))
    // Minus sign
    scope.drawLine(Color.White, Offset(cx2 - 10f, cy), Offset(cx2 + 10f, cy), strokeWidth = 3f)

    // Field lines pointing inward to -q
    for (deg in angles) {
        val rad = Math.toRadians(deg)
        val outerX = cx2 + (r + 46f) * cos(rad).toFloat()
        val outerY = cy + (r + 46f) * sin(rad).toFloat()
        val innerX = cx2 + (r + 4f) * cos(rad).toFloat()
        val innerY = cy + (r + 4f) * sin(rad).toFloat()
        scope.drawLine(Color(0xFF38BDF8), Offset(outerX, outerY), Offset(innerX, innerY), strokeWidth = 2.5f)
        drawArrowHead(scope, Offset(outerX, outerY), Offset(innerX, innerY), Color(0xFF38BDF8))
    }
}

// 2. Coulomb's Law Force Vectors
private fun drawCoulombForce(scope: DrawScope) {
    val width = scope.size.width
    val height = scope.size.height
    val cy = height / 2f
    val cx1 = width * 0.35f
    val cx2 = width * 0.65f
    val r = 22f

    // Charge 1 (+q1)
    scope.drawCircle(Color(0xFFEF4444), radius = r, center = Offset(cx1, cy))
    scope.drawLine(Color.White, Offset(cx1 - 8f, cy), Offset(cx1 + 8f, cy), strokeWidth = 3f)
    scope.drawLine(Color.White, Offset(cx1, cy - 8f), Offset(cx1, cy + 8f), strokeWidth = 3f)

    // Charge 2 (+q2)
    scope.drawCircle(Color(0xFFEF4444), radius = r, center = Offset(cx2, cy))
    scope.drawLine(Color.White, Offset(cx2 - 8f, cy), Offset(cx2 + 8f, cy), strokeWidth = 3f)
    scope.drawLine(Color.White, Offset(cx2, cy - 8f), Offset(cx2, cy + 8f), strokeWidth = 3f)

    // Distance line r
    scope.drawLine(
        Color(0xFF94A3B8),
        Offset(cx1, cy + 40f),
        Offset(cx2, cy + 40f),
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    // Repulsion force arrows
    // F21 on q1 to left
    scope.drawLine(Color(0xFF00ADB5), Offset(cx1 - r, cy), Offset(cx1 - r - 55f, cy), strokeWidth = 3.5f)
    drawArrowHead(scope, Offset(cx1 - r, cy), Offset(cx1 - r - 55f, cy), Color(0xFF00ADB5))

    // F12 on q2 to right
    scope.drawLine(Color(0xFF00ADB5), Offset(cx2 + r, cy), Offset(cx2 + r + 55f, cy), strokeWidth = 3.5f)
    drawArrowHead(scope, Offset(cx2 + r, cy), Offset(cx2 + r + 55f, cy), Color(0xFF00ADB5))
}

// 3. Field Lines interaction between + and - (Electric Dipole Lines)
private fun drawFieldLinesInteraction(scope: DrawScope) {
    val width = scope.size.width
    val height = scope.size.height
    val cy = height / 2f
    val cx1 = width * 0.28f
    val cx2 = width * 0.72f
    val r = 20f

    // Charges
    scope.drawCircle(Color(0xFFEF4444), radius = r, center = Offset(cx1, cy))
    scope.drawCircle(Color(0xFF3B82F6), radius = r, center = Offset(cx2, cy))
    scope.drawLine(Color.White, Offset(cx1 - 8f, cy), Offset(cx1 + 8f, cy), strokeWidth = 2.5f)
    scope.drawLine(Color.White, Offset(cx1, cy - 8f), Offset(cx1, cy + 8f), strokeWidth = 2.5f)
    scope.drawLine(Color.White, Offset(cx2 - 8f, cy), Offset(cx2 + 8f, cy), strokeWidth = 2.5f)

    // Central straight line from +q to -q
    scope.drawLine(Color(0xFF38BDF8), Offset(cx1 + r, cy), Offset(cx2 - r, cy), strokeWidth = 2f)
    drawArrowHead(scope, Offset(cx1 + r, cy), Offset((cx1 + cx2) / 2f + 10f, cy), Color(0xFF38BDF8))

    // Curved lines top
    val path1 = Path().apply {
        moveTo(cx1 + r * 0.7f, cy - r * 0.7f)
        cubicTo(cx1 + 30f, cy - 70f, cx2 - 30f, cy - 70f, cx2 - r * 0.7f, cy - r * 0.7f)
    }
    scope.drawPath(path1, Color(0xFF38BDF8), style = Stroke(width = 2f))

    val path2 = Path().apply {
        moveTo(cx1, cy - r)
        cubicTo(cx1 + 20f, cy - 110f, cx2 - 20f, cy - 110f, cx2, cy - r)
    }
    scope.drawPath(path2, Color(0xFF38BDF8), style = Stroke(width = 2f))

    // Curved lines bottom
    val path3 = Path().apply {
        moveTo(cx1 + r * 0.7f, cy + r * 0.7f)
        cubicTo(cx1 + 30f, cy + 70f, cx2 - 30f, cy + 70f, cx2 - r * 0.7f, cy + r * 0.7f)
    }
    scope.drawPath(path3, Color(0xFF38BDF8), style = Stroke(width = 2f))

    val path4 = Path().apply {
        moveTo(cx1, cy + r)
        cubicTo(cx1 + 20f, cy + 110f, cx2 - 20f, cy + 110f, cx2, cy + r)
    }
    scope.drawPath(path4, Color(0xFF38BDF8), style = Stroke(width = 2f))
}

// 4. Electric Dipole Structure (-q and +q separated by 2a, vector p)
private fun drawElectricDipole(scope: DrawScope) {
    val width = scope.size.width
    val height = scope.size.height
    val cy = height / 2f
    val cx1 = width * 0.3f
    val cx2 = width * 0.7f
    val midX = (cx1 + cx2) / 2f
    val r = 22f

    // Axial line
    scope.drawLine(Color(0xFF475569), Offset(width * 0.1f, cy), Offset(width * 0.9f, cy), strokeWidth = 1.5f)

    // Equatorial line
    scope.drawLine(
        Color(0xFF475569),
        Offset(midX, height * 0.15f),
        Offset(midX, height * 0.85f),
        strokeWidth = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
    )

    // Dipole charges: -q at left, +q at right
    scope.drawCircle(Color(0xFF3B82F6), radius = r, center = Offset(cx1, cy))
    scope.drawLine(Color.White, Offset(cx1 - 8f, cy), Offset(cx1 + 8f, cy), strokeWidth = 3f)

    scope.drawCircle(Color(0xFFEF4444), radius = r, center = Offset(cx2, cy))
    scope.drawLine(Color.White, Offset(cx2 - 8f, cy), Offset(cx2 + 8f, cy), strokeWidth = 3f)
    scope.drawLine(Color.White, Offset(cx2, cy - 8f), Offset(cx2, cy + 8f), strokeWidth = 3f)

    // Center point O
    scope.drawCircle(Color.White, radius = 4f, center = Offset(midX, cy))

    // Dipole moment vector p (from -q to +q)
    scope.drawLine(Color(0xFF00ADB5), Offset(cx1 + 10f, cy - 40f), Offset(cx2 - 10f, cy - 40f), strokeWidth = 4f)
    drawArrowHead(scope, Offset(cx1 + 10f, cy - 40f), Offset(cx2 - 10f, cy - 40f), Color(0xFF00ADB5))

    // Dimension 2a line
    scope.drawLine(Color(0xFFF59E0B), Offset(cx1, cy + 38f), Offset(cx2, cy + 38f), strokeWidth = 2f)
}

// 5. Electric Dipole in Uniform Field (Torque τ = p × E)
private fun drawDipoleInUniformField(scope: DrawScope) {
    val width = scope.size.width
    val height = scope.size.height

    // Uniform field lines E pointing to the right
    val lineYs = listOf(height * 0.2f, height * 0.4f, height * 0.6f, height * 0.8f)
    for (y in lineYs) {
        scope.drawLine(Color(0xFF1E3A8A), Offset(width * 0.08f, y), Offset(width * 0.92f, y), strokeWidth = 2f)
        drawArrowHead(scope, Offset(width * 0.08f, y), Offset(width * 0.92f, y), Color(0xFF38BDF8))
    }

    val cx = width / 2f
    val cy = height / 2f
    val angle = Math.toRadians(35.0)
    val len = 80f

    val x1 = cx - (len * cos(angle)).toFloat()
    val y1 = cy + (len * sin(angle)).toFloat() // -q at bottom-left

    val x2 = cx + (len * cos(angle)).toFloat()
    val y2 = cy - (len * sin(angle)).toFloat() // +q at top-right

    // Dipole axis line
    scope.drawLine(Color.White, Offset(x1, y1), Offset(x2, y2), strokeWidth = 3f)

    // -q Charge
    scope.drawCircle(Color(0xFF3B82F6), radius = 18f, center = Offset(x1, y1))
    scope.drawLine(Color.White, Offset(x1 - 6f, y1), Offset(x1 + 6f, y1), strokeWidth = 2.5f)

    // +q Charge
    scope.drawCircle(Color(0xFFEF4444), radius = 18f, center = Offset(x2, y2))
    scope.drawLine(Color.White, Offset(x2 - 6f, y2), Offset(x2 + 6f, y2), strokeWidth = 2.5f)
    scope.drawLine(Color.White, Offset(x2, y2 - 6f), Offset(x2, y2 + 6f), strokeWidth = 2.5f)

    // Force F1 on +q (to right in field direction)
    scope.drawLine(Color(0xFF10B981), Offset(x2, y2), Offset(x2 + 55f, y2), strokeWidth = 3.5f)
    drawArrowHead(scope, Offset(x2, y2), Offset(x2 + 55f, y2), Color(0xFF10B981))

    // Force F2 on -q (to left opposite field direction)
    scope.drawLine(Color(0xFF10B981), Offset(x1, y1), Offset(x1 - 55f, y1), strokeWidth = 3.5f)
    drawArrowHead(scope, Offset(x1, y1), Offset(x1 - 55f, y1), Color(0xFF10B981))
}

// 6. Gaussian Surface and Electric Flux
private fun drawGaussianSurfaceFlux(scope: DrawScope) {
    val width = scope.size.width
    val height = scope.size.height
    val cx = width / 2f
    val cy = height / 2f

    // Closed spherical Gaussian Surface (dashed cyan)
    scope.drawCircle(
        color = Color(0xFF00ADB5),
        radius = 75f,
        center = Offset(cx, cy),
        style = Stroke(width = 2.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f))
    )

    // Enclosed charge +q at center
    scope.drawCircle(Color(0xFFEF4444), radius = 20f, center = Offset(cx, cy))
    scope.drawLine(Color.White, Offset(cx - 8f, cy), Offset(cx + 8f, cy), strokeWidth = 3f)
    scope.drawLine(Color.White, Offset(cx, cy - 8f), Offset(cx, cy + 8f), strokeWidth = 3f)

    // Flux lines crossing Gaussian boundary outwards
    val angles = listOf(15.0, 60.0, 110.0, 160.0, 205.0, 255.0, 300.0, 345.0)
    for (deg in angles) {
        val rad = Math.toRadians(deg)
        val startX = cx + 22f * cos(rad).toFloat()
        val startY = cy + 22f * sin(rad).toFloat()
        val endX = cx + 105f * cos(rad).toFloat()
        val endY = cy + 105f * sin(rad).toFloat()
        scope.drawLine(Color(0xFF38BDF8), Offset(startX, startY), Offset(endX, endY), strokeWidth = 2f)
        drawArrowHead(scope, Offset(startX, startY), Offset(endX, endY), Color(0xFF38BDF8))
    }

    // Surface element dA at right
    val daX = cx + 75f
    val daY = cy
    scope.drawCircle(Color(0xFFF59E0B), radius = 5f, center = Offset(daX, daY))
    scope.drawLine(Color(0xFFF59E0B), Offset(daX, daY), Offset(daX + 45f, daY), strokeWidth = 3f)
    drawArrowHead(scope, Offset(daX, daY), Offset(daX + 45f, daY), Color(0xFFF59E0B))
}

private fun drawArrowHead(scope: DrawScope, start: Offset, end: Offset, color: Color) {
    val angle = atan2((end.y - start.y).toDouble(), (end.x - start.x).toDouble())
    val arrowLen = 14f
    val arrowAngle = Math.toRadians(25.0)

    val x1 = (end.x - arrowLen * cos(angle - arrowAngle)).toFloat()
    val y1 = (end.y - arrowLen * sin(angle - arrowAngle)).toFloat()

    val x2 = (end.x - arrowLen * cos(angle + arrowAngle)).toFloat()
    val y2 = (end.y - arrowLen * sin(angle + arrowAngle)).toFloat()

    val arrowPath = Path().apply {
        moveTo(end.x, end.y)
        lineTo(x1, y1)
        lineTo(x2, y2)
        close()
    }
    scope.drawPath(arrowPath, color)
}
