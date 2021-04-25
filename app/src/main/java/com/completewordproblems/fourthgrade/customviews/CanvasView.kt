package com.completewordproblems.fourthgrade.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

private const val TOLERANCE = 5
private const val DRAW_MODE_DRAW = 0
private const val DRAW_MODE_ERASE = 1

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs) {
    var path = Path()
    private val penPaths = mutableListOf<Path>()
    private val erasePaths = mutableListOf<Path>()
    var penPaint = Paint()
    var erasePaint = Paint()
    var xPos = 0.0f
    var yPos = 0.0f
    var drawMode = DRAW_MODE_DRAW
    lateinit var bitmap: Bitmap
    lateinit var canvas: Canvas

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
        erasePaint.strokeWidth = 18f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        penPaths.forEach { canvas?.drawPath(it, penPaint) }
        erasePaths.forEach { canvas?.drawPath(it, erasePaint) }
        if (drawMode == DRAW_MODE_DRAW) {
            canvas?.drawPath(path, penPaint)
        } else {
            canvas?.drawPath(path, erasePaint)
        }
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

    public fun switchToErase() {
        drawMode = DRAW_MODE_ERASE
    }

    public fun switchToDraw() {
        drawMode = DRAW_MODE_DRAW
    }

    fun undo() {
        try {
            penPaths.removeLast().reset()
        } catch (e: NoSuchElementException) {
            Log.e("TAG", e.message.toString())
        }
        invalidate()
    }

    public fun clearCanvas() {
        penPaths.forEach { it.reset() }
        penPaths.clear()
        erasePaths.forEach { it.reset() }
        erasePaths.clear()
        path.reset()
        invalidate()
    }

    private fun upTouch() {
        path.lineTo(xPos, yPos)
        if (drawMode == DRAW_MODE_DRAW) {
            penPaths.add(path)
        } else {
            erasePaths.add(path)
        }
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