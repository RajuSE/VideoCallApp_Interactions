package com.devx.call_interactions.main.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GlowView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var disable = false
    private var alpha = 0
    private var increase = true
    private val p = Paint()

    init {
        p.isAntiAlias = true
        p.color = Color.BLACK
        p.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        p.alpha = alpha
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), p)
        if (increase) alpha += 4
        else alpha -= 4
        if (alpha >= 200) {
            increase = false
        }
        if (alpha <= 4) {
            increase = true
        }
        if (!disable) invalidate()
    }

    fun disable() {
        alpha = 0
        disable = true
        invalidate()
    }

    fun enable() {
        disable = false
        invalidate()
    }
}
