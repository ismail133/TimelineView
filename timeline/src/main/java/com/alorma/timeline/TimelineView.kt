package com.alorma.timeline

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

class TimelineView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        private const val CIRCLE_SIZE = 30

        private const val V_ALIGN_TOP: Int = 0
        private const val V_ALIGN_CENTER: Int = 1
        private const val V_ALIGN_BOTTOM: Int = 2

        private const val V_ALIGN_DEFAULT: Int = V_ALIGN_CENTER

        private const val H_ALIGN_START: Int = 0
        private const val H_ALIGN_CENTER: Int = 1
        private const val H_ALIGN_END: Int = 2

        private const val H_ALIGN_DEFAULT: Int = H_ALIGN_CENTER
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
    }

    private val linesPaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        strokeWidth = 1f
    }

    private val rect = Rect()

    private val vAlign: Int
    private val hAlign: Int

    init {
        val typedArray: TypedArray = getTypedArray(context, attrs, defStyleAttr)

        vAlign = typedArray.getInt(R.styleable.TimelineView_vAlign, V_ALIGN_DEFAULT)
        hAlign = typedArray.getInt(R.styleable.TimelineView_hAlign, V_ALIGN_DEFAULT)
    }

    private fun getTypedArray(context: Context, attrs: AttributeSet?, defStyle: Int): TypedArray {
        return context.theme
                .obtainStyledAttributes(attrs, R.styleable.TimelineView, defStyle, 0)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        canvas.getClipBounds(rect)

        val centerX = rect.centerX()
        val centerY = rect.centerY()

        val x = when (hAlign) {
            H_ALIGN_START -> ((centerY.toFloat()) / 3)
            H_ALIGN_END -> centerY + ((centerY.toFloat()) / 3) * 2
            else -> centerX.toFloat()
        }
        val y = when (vAlign) {
            V_ALIGN_TOP -> ((centerX.toFloat()) / 3)
            V_ALIGN_BOTTOM -> centerX + ((centerX.toFloat()) / 3) * 2
            else -> centerY.toFloat()
        }.toFloat()

        canvas.drawCircle(x, y, (CIRCLE_SIZE / 2).toFloat(), paint)

        // V lines
        val top = 0f
        val bottom = rect.bottom.toFloat()

        canvas.drawLine(((centerX.toFloat()) / 3), top, ((centerX.toFloat()) / 3), bottom, linesPaint)
        canvas.drawLine(centerX.toFloat(), top, centerX.toFloat(), bottom, linesPaint)
        canvas.drawLine(centerX + ((centerX.toFloat()) / 3) * 2, top, centerX + ((centerX.toFloat()) / 3) * 2, bottom, linesPaint)

        // H lines
        val left = 0f
        val right = rect.right.toFloat()
        canvas.drawLine(left, ((centerY.toFloat()) / 3), right, ((centerY.toFloat()) / 3), linesPaint)
        canvas.drawLine(left, centerY.toFloat(), right, centerY.toFloat(), linesPaint)
        canvas.drawLine(left, centerY + ((centerY.toFloat()) / 3) * 2, right, centerY + ((centerY.toFloat()) / 3) * 2, linesPaint)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}