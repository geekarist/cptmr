package me.cpele.cptmr.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.min

class CircularTimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintStrokeWidth = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        4f,
        resources.displayMetrics
    )

    private val halfStrokeWidth = paintStrokeWidth / 2

    private val smallStrokeWidth = paintStrokeWidth * 2f / 3f

    private val framePaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_red_dark, context.theme)
        strokeWidth = paintStrokeWidth
        style = Paint.Style.STROKE
    }

    private val smallFramePaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_blue_light, context.theme)
        strokeWidth = smallStrokeWidth
        style = Paint.Style.STROKE
    }

    private val mnHandPaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_red_light, context.theme)
        alpha = 127
        style = Paint.Style.FILL
    }

    private val hrHandPaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_blue_light, context.theme)
        alpha = 191
        style = Paint.Style.FILL
    }

    private var hour: Int? = null
    private var sweepAngleHr: Float = 0f

    private var minute: Int? = null
    private var sweepAngleMn: Float = 0f

    fun setTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
        sweepAngleMn = computeMnAngle(minute)
        sweepAngleHr = computeHrAngle(hour)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {

        // Clock frame parameters
        val halfWidth = measuredWidth / 2f
        val halfHeight = measuredHeight / 2f
        val radius = min(halfWidth, halfHeight) - halfStrokeWidth

        // Clock hand parameters (minutes)
        val mnLeft = halfWidth - radius
        val mnTop = halfHeight - radius
        val mnRight = halfWidth + radius
        val mnBottom = halfHeight + radius

        // Clock hand parameters (hours)
        val smallRadius = radius * 2f / 3f
        val hrLeft = halfWidth - smallRadius
        val hrTop = halfHeight - smallRadius
        val hrRight = halfWidth + smallRadius
        val hrBottom = halfHeight + smallRadius

        // Draw hours
        canvas.drawArc(
            hrLeft,
            hrTop,
            hrRight,
            hrBottom,
            -90f,
            sweepAngleHr,
            true,
            hrHandPaint
        )

        // Draw minutes
        canvas.drawArc(
            mnLeft,
            mnTop,
            mnRight,
            mnBottom,
            -90f,
            sweepAngleMn,
            true,
            mnHandPaint
        )

        // Draw frames
        canvas.drawCircle(halfWidth, halfHeight, radius, framePaint)
        canvas.drawCircle(halfWidth, halfHeight, smallRadius, smallFramePaint)
    }
}

private fun computeHrAngle(hour: Int): Float {
    val hourLimited = hour % 24 // Minute could be > 24
    val hourFactor = hourLimited / 24f // Between 0 and 1
    return 360 * hourFactor // Between 0 and 360

    // Ignore hour for now
}

private fun computeMnAngle(minute: Int): Float {
    val minuteLimited = minute % 60 // Minute could be > 60
    val minuteFactor = minuteLimited / 60f // Between 0 and 1
    return 360 * minuteFactor // Between 0 and 360

    // Ignore hour for now
}

