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

    private val paint = Paint().apply {
        color = resources.getColor(android.R.color.holo_red_dark, context.theme)
        strokeWidth = paintStrokeWidth
        style = Paint.Style.STROKE
    }

    fun setTime(hour: Int, minute: Int) {
        // TODO
    }

    override fun onDraw(canvas: Canvas) {

        // Draw background
        val halfWidth = measuredWidth / 2f
        val halfHeight = measuredHeight / 2f
        val radius = min(halfWidth, halfHeight) - halfStrokeWidth
        canvas.drawCircle(halfWidth, halfHeight, radius, paint)

        // TODO: Draw time
    }
}