package com.completewordproblems.fourthgrade.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

private const val TOLERANCE = 5

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context) {
    var path = Path()
    val paths = mutableListOf<Path>()
    var paint = Paint()
    var xPos = 0.0f
    var yPos = 0.0f
    lateinit var bitmap: Bitmap
    lateinit var canvas: Canvas

    init {
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND

        // TODO: 4/24/21 make this density independent
        paint.strokeWidth = 12f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
        paths.forEach { canvas?.drawPath(it, paint) }
    }

    private fun startTouch(x: Float, y: Float) {
        path = Path()
        path.moveTo(x, y)
        xPos = x
        yPos = y
    }

    private fun moveTouch(x: Float, y: Float) {
        val dx = abs(x - xPos)
        val dy = abs(y - yPos)
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(xPos, yPos, (x + xPos) / 2, (y + yPos) / 2)
            xPos = x
            yPos = y
        }
    }

    public fun clearCanvas() {
        paths.forEach { it.reset() }
        paths.clear()
        path.reset()
        invalidate()
    }

    private fun upTouch() {
        path.lineTo(xPos, yPos)
        paths.add(path)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val x = event.x
            val y = event.y


            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTouch(x, y); invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    moveTouch(x, y); invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    upTouch(); invalidate()
                }
            }
        }
        return true
    }
}