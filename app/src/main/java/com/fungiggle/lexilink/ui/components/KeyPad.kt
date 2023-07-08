package com.fungiggle.lexilink.ui.components

import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fungiggle.lexilink.models.KeyPadButton
import com.fungiggle.lexilink.utils.TAG
import java.lang.Math.cos
import java.lang.Math.sin

@Composable
fun KeyPad(
    radiusMain:Float,
    list:List<KeyPadButton>,
    onArgsObtained:(center:Offset,size:Size)->Unit = {_,_,->}
) {
    val paddingCanvas = 40.dp
    Box(modifier = Modifier
        .padding(top = 50.dp)
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier =
        Modifier
            .fillMaxSize()
            .padding(paddingCanvas)
        ) {
            Log.e(TAG,"Canvas Drawn")
            onArgsObtained(center,size)
            val buttonRadius = size.width * 0.08f
            val offsetAngle = 360f/list.size
            // Draw buttons
            repeat(list.count()) { index->
                val angle = index * offsetAngle
                val offset = getButtonCenter(center,radiusMain,angle)
                //Preparing button to draw
                list[index].center = offset
                val button = list[index]
                //End Preparing

                // Draw button circle
                val listColorsDefault = listOf(Color(0xFF904703),Color(0xFFFDCC9F))
                val brushLinear = Brush.linearGradient(
                    colors = listColorsDefault,
                    start = Offset(
                        button.center.x + (buttonRadius),
                        button.center.y + (buttonRadius)
                    ),
                    end = Offset(
                        button.center.x - (buttonRadius),
                        button.center.y - (buttonRadius)
                    )
                )
                val brushBorderLinearTouched =  Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFFFF),Color(0xFF262525)),
                    start = Offset(
                        button.center.x + (buttonRadius),
                        button.center.y + (buttonRadius)),
                    end = Offset(button.center.x - (buttonRadius),
                        button.center.y - (buttonRadius))
                )
                val brushBorderLinearDefault =  Brush.linearGradient(
                    colors = listOf(Color(0xFF262525),Color(0xFFFFFFFF)),
                    start = Offset(
                        button.center.x + (buttonRadius),
                        button.center.y + (buttonRadius)),
                    end = Offset(button.center.x - (buttonRadius),
                        button.center.y - (buttonRadius))
                )

                //Button Circle
                drawCircle(
                    brush =brushLinear,
                    center = button.center,
                    radius = buttonRadius
                )

                drawCircle(
                    brush = brushBorderLinearDefault,
                    center = button.center,
                    radius = buttonRadius+5,
                    style = Stroke(width = 10f)
                )

                // Draw text in the center of the button circle
                drawIntoCanvas { canvas ->
                    val text = button.label
                    val textPaint = Paint().apply {
                        color = Color.White.toArgb()
                        textAlign = Paint.Align.CENTER
                        textSize = 24.dp.toPx()
                    }
                    val textBounds = Rect()
                    textPaint.getTextBounds(text, 0, text.length, textBounds)
                    val textX = button.center.x
                    val textY = button.center.y + (textBounds.height()/2f)
                    canvas.nativeCanvas.drawText(text, textX, textY, textPaint)
                }
            }
        }
    }
}

// Helper function to calculate button center based on angle
private fun getButtonCenter(center: Offset, radius: Float, angle: Float): Offset {
    val radians = Math.toRadians(angle.toDouble())
    val x = center.x + (radius * cos(radians)).toFloat()
    val y = center.y + (radius * sin(radians)).toFloat()
    return Offset(x, y)
}
