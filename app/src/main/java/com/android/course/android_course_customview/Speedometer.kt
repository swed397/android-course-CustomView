package com.android.course.android_course_customview

import android.R.attr.radius
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin


class Speedometer(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    private var speed: Int = 0
    private var maxSpeed: Int = 0

    @ColorInt
    private var colorLowSpeed = Color.BLACK

    @ColorInt
    private var colorMediumSpeed = Color.BLACK

    @ColorInt
    private var colorFastSpeed = Color.BLACK

    @ColorInt
    private var colorArrow = Color.BLACK

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }
    private val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.blue)
        strokeWidth = 0.009f
    }

    private val bigScalePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.purple)
        strokeWidth = 0.015f
    }

    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.orange)
        strokeWidth = 0.02f
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 1f
        color = ContextCompat.getColor(context, R.color.orange)
        style = Paint.Style.FILL
        textAlign = Paint.Align.LEFT
    }

    private val speedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.purple)
    }

    private val speedToDraw = ArrayDeque<Pair<String, Pair<Float, Float>>>()

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.Speedometer,
            R.attr.speedometerDefaultsStyles,
            R.style.speedometerDefaultsStyles
        )

        try {
            speed = typedArray.getInt(R.styleable.Speedometer_speed, 50)
            println(speed)
            maxSpeed = typedArray.getInt(R.styleable.Speedometer_maxSpeed, 100)
            colorLowSpeed = typedArray.getColor(R.styleable.Speedometer_colorLowSpeed, Color.BLACK)
            colorMediumSpeed =
                typedArray.getColor(R.styleable.Speedometer_colorMediumSpeed, Color.BLACK)
            colorFastSpeed =
                typedArray.getColor(R.styleable.Speedometer_colorFastSpeed, Color.BLACK)
            colorArrow = typedArray.getColor(R.styleable.Speedometer_colorArrow, Color.BLACK)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawBackground(canvas)
        drawSpeed(canvas)
        drawScale(canvas)
        drawArrow(canvas)
    }

    fun speedUp() {
        speed += 1
        invalidate()
    }

    fun speedDown() {
        speed -= 1
        invalidate()
    }

    private fun drawBackground(canvas: Canvas?) {
        canvas?.apply {
            scale(.5f * width, -1f * height)
            translate(1f, -1f)
            drawCircle(0f, 0f, 1f, backgroundPaint)
            drawCircle(0f, 0f, 0.8f,
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    style = Paint.Style.FILL
                    color = ContextCompat.getColor(context, R.color.lightGrey)
                })
            save()
        }
    }

    private fun drawScale(canvas: Canvas?) {
        val scaleStep = Math.PI / maxSpeed
        val scaleLength = 0.9f
        val halfCircumference = radius * Math.PI


        for (i in 0..maxSpeed) {
            val x1 = cos(Math.PI - scaleStep * i).toFloat()
            val y1 = sin(Math.PI - scaleStep * i).toFloat()
            if (i % 20 == 0) {
                val x2 = x1 * scaleLength * 0.9f
                val y2 = y1 * scaleLength * 0.9f
                canvas?.drawLine(x1, y1, x2, y2, bigScalePaint)
                speedToDraw.addFirst(Pair("$i", Pair(x2, y2)))
//                drawText(canvas)
            } else {

                if (i < maxSpeed / 100 * 40)
                    scalePaint.color = ContextCompat.getColor(context, R.color.green)
                else if (i < maxSpeed / 100 * 80) {
                    scalePaint.color = ContextCompat.getColor(context, R.color.yellow)
                } else {
                    scalePaint.color = ContextCompat.getColor(context, R.color.red)
                }

                val x2 = x1 * scaleLength
                val y2 = y1 * scaleLength
                canvas?.drawLine(x1, y1, x2, y2, scalePaint)
            }
        }
    }

    private fun drawArrow(canvas: Canvas?) {
        canvas?.apply {
            rotate(90 - 180f * (speed.toFloat() / maxSpeed.toFloat()))
            drawLine(0f, 0f, 0f, 1f, arrowPaint)
            drawCircle(0f, 0f, 0.05f,
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = ContextCompat.getColor(context, R.color.orange)
                })
        }
    }

//    private fun drawText(canvas: Canvas?) {
//        canvas?.apply {
////            rotate(90f)
//            for (i in speedToDraw) {
//                println(i.second.first.toString() + i.second.second.toString())
//                drawText(i.first, i.second.first, i.second.second, textPaint)
//            }
//        }
//    }

    private fun drawSpeed(canvas: Canvas?) {
        canvas?.apply {
            save()
            translate(width.toFloat() / 2, 0f)
            rotate(180f)
            drawRect(-0.3f, -0.3f, 0.3f, 0f, speedPaint)
            drawText("11", 0.01f, -0.01f, textPaint)
            rotate(180f)
            restore()
        }
    }
}