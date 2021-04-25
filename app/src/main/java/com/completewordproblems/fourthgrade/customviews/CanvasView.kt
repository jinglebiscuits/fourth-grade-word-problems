package com.completewordproblems.fourthgrade.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

private const val TOLERANCE = 5

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    var path = MyPath(DRAW_MODE_DRAW)
    private val paths = mutableListOf<MyPath>()
    var penPaint = Paint()
    var erasePaint = Paint()
    var xPos = 0.0f
    var yPos = 0.0f
    var drawMode = DRAW_MODE_DRAW
    lateinit var bitmap: Bitmap
    lateinit var canvas: Canvas
    lateinit var drawLayout: DrawLayout

    init {
        penPaint.isAntiAlias = true
        penPaint.color = Color.BLACK
        penPaint.style = Paint.Style.STROKE
        penPaint.strokeJoin = Paint.Join.ROUND

        // TODO: 4/24/21 make this density independent
        penPaint.strokeWidth = 12f

        erasePaint.isAntiAlias = true
        erasePaint.color = Color.WHITE
        erasePaint.style = Paint.Style.STROKE
        erasePaint.strokeJoin = Paint.Join.ROUND
        erasePaint.strokeWidth = 30f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paths.forEach {
            canvas?.drawPath(
                it,
                if (it.type == DRAW_MODE_DRAW) penPaint else erasePaint
            )
        }
        if (drawMode == DRAW_MODE_DRAW) {
            canvas?.drawPath(path, penPaint)
        } else {
            canvas?.drawPath(path, erasePaint)
        }
        drawLayout.onDrawingChanged()
    }

    private fun startTouch(x: Float, y: Float) {
        path = MyPath(drawMode)
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

    public fun switchToErase() {
        drawMode = DRAW_MODE_ERASE
    }

    public fun switchToDraw() {
        drawMode = DRAW_MODE_DRAW
    }

    fun undo() {
        try {
            paths.removeLast().reset()
        } catch (e: NoSuchElementException) {
            Log.e("TAG", e.message.toString())
        }
        invalidate()
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
        paths.sortBy { it.creationTime }
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

    companion object {
        public const val DRAW_MODE_DRAW = 0
        public const val DRAW_MODE_ERASE = 1
    }
}