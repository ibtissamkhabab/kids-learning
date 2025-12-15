package com.example.enfantapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.parseColor("#2196F3")//couleur de trace
        strokeWidth = 20f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND //arrondi
        isAntiAlias = true
    }

    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val path = Path()
    private val paths = mutableListOf<Pair<Path, Paint>>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (w > 0 && h > 0) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap!!)
            // Transparent au lieu de blanc pour voir la lettre en dessous
            canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }

            MotionEvent.ACTION_UP -> {
                canvas?.drawPath(path, paint)

                val newPath = Path(path)
                val newPaint = Paint(paint)
                paths.add(Pair(newPath, newPaint))

                path.reset()
            }

            else -> return false
        }

        invalidate()
        return true
    }

    fun clear() {
        path.reset()
        paths.clear()
        // Effacer en transparent pour voir la lettre
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    fun undo() {
        if (paths.isNotEmpty()) {
            paths.removeAt(paths.size - 1)
            redrawAll()
        }
    }

    private fun redrawAll() {
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        for ((path, paint) in paths) {
            canvas?.drawPath(path, paint)
        }

        invalidate()
    }

    fun setDrawColor(color: Int) {
        paint.color = color
    }

    fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width
    }
}
