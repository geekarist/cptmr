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
        8f,
        resources.displayMetrics
    )

    private val halfStrokeWidth = paintStrokeWidth / 2

    private val circlePaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_red_dark, context.theme)
        strokeWidth = paintStrokeWidth
        style = Paint.Style.STROKE
    }

    private val arcPaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_red_light, context.theme)
        style = Paint.Style.FILL
    }

    private var hour: Int? = null
    private var minute: Int? = null
    private var sweepAngle: Float = 0f

    fun setTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
        sweepAngle = computeAngle(hour, minute)
        invalidate()
    }

    private fun computeAngle(hour: Int, minute: Int): Float {
        val minuteLimited = minute % 60 // Minute could be > 60
        val minuteFactor = minuteLimited / 60f // Between 0 and 1
        return 360 * minuteFactor // Between 0 and 360

        // Ignore hour for now
    }

    override fun onDraw(canvas: Canvas) {

        // Clock frame parameters
        val halfWidth = measuredWidth / 2f
        val halfHeight = measuredHeight / 2f
        val radius = min(halfWidth, halfHeight) - halfStrokeWidth

        // Clock hands parameters
        val left = halfWidth - radius
        val top = halfHeight - radius
        val right = halfWidth + radius
        val bottom = halfHeight + radius

        canvas.drawArc(
            left,
            top,
            right,
            bottom,
            -90f,
            sweepAngle,
            true,
            arcPaint
        )

        canvas.drawCircle(halfWidth, halfHeight, radius, circlePaint)
    }
}