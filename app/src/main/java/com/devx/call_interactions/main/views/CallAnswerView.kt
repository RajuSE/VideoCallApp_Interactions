package com.devx.call_interactions.main.views

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.MotionEventCompat
import androidx.customview.widget.ViewDragHelper
import com.devx.call_interactions.main.CallerScreenListener
import kotlin.math.max
import kotlin.math.min

class CallAnswerView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val helper: ViewDragHelper
    private var listener: CallerScreenListener? = null
    private var initY = 0f
    private var isFirst = true

    fun setListener(listener: CallerScreenListener?) {
        this.listener = listener
    }

    init {
        helper = ViewDragHelper.create(this, 1.0f, HelperCallback())
    }

    fun moveToBottom() {
        val v = getChildAt(0) //hardcoded view index //use IDs later
        //        helper.smoothSlideViewTo(v, 0, getHeight() - v.getHeight() - (int)v.getY());
        val h = Handler()
        h.postDelayed({
            v.offsetTopAndBottom(10)
            if (initY > v.y) moveToBottom()
            else listener!!.reachedDown()
        }, 2)


        //        v.animate().y(getHeight() - v.getHeight() - (int)v.getY());
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (isFirst) {
            val v = getChildAt(0)
            initY = v.y
            isFirst = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        helper.processTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            listener!!.released()
        } else {
            listener!!.clickedAnswer()
        }
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(ev)
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            helper.cancel()
        }
        return helper.shouldInterceptTouchEvent(ev)
    }

    inner class HelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(view: View, i: Int): Boolean {
            return true
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val topBound = paddingTop
            val bottomBound = height - child.height

            val newTop = min(max(top.toDouble(), topBound.toDouble()), bottomBound.toDouble())
                .toInt()
            val v = getChildAt(0)
            if (initY - newTop >= Resources.getSystem().displayMetrics.density * 90) {
                listener!!.answered()
            }

            return newTop
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return width / 2 - child.width / 2
        }
    }
}
