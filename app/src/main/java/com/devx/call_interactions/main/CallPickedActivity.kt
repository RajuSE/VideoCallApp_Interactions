package com.devx.call_interactions.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.devx.call_interactions.R
import com.devx.call_interactions.main.views.GlowView

@Suppress("DEPRECATION")
class CallPickedActivity : AppCompatActivity() {
    private lateinit var topView: View
    private lateinit var bottomView: View
    private lateinit var rootlayout: ConstraintLayout
    private lateinit var addTv: TextView
    private lateinit var ringing3: TextView

    private lateinit var caller1: View
    private lateinit var caller2: View
    private lateinit var caller3: View
    private lateinit var caller4: View
    private lateinit var glowView: GlowView
    private lateinit var ivDisconnect: ImageView

    private val initialSet = ConstraintSet()
    private val split3 = ConstraintSet()
    private val split4 = ConstraintSet()

    private var hidden = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_call_picked)

        topView = findViewById(R.id.caller_top_view)
        bottomView = findViewById(R.id.caller_bottom_view)
        rootlayout = findViewById(R.id.caller_root_view)
        ivDisconnect = findViewById(R.id.ivDisconnect)
        addTv = findViewById(R.id.add)
        ringing3 = findViewById(R.id.ringing3)

        caller1 = findViewById(R.id.caller_1)
        caller2 = findViewById(R.id.caller_2)
        caller3 = findViewById(R.id.caller_3)
        caller4 = findViewById(R.id.caller_4)
        glowView = findViewById(R.id.glowView)

        addCaller()

        rootlayout.setOnClickListener {
            if (hidden) {
                AnimUtils.animateDownSlow(topView, null, 300)
                AnimUtils.animateBottomShow(bottomView, null, 300)
                hidden = false
            } else {
                AnimUtils.animateUpSlow(topView, null, 300)
                AnimUtils.animateBottomHide(bottomView, null, 300)
                hidden = true
            }
        }
        ivDisconnect.setOnClickListener {
            Toast.makeText(this, "Call disconnected..", Toast.LENGTH_SHORT).show()
            finish()
        }
        topView.setOnClickListener {
            if (caller2.visibility == View.GONE) caller2.setVisibility(View.VISIBLE)
            else caller2.visibility = View.GONE
        }

        addTv.setOnClickListener {
            when (count) {
                1 -> {
                    count = 2
                    ringing3.visibility = View.VISIBLE
                    ringing3.text = getString(R.string.ringing)
                    ringing3.invalidate()
                    glowView.enable()
                    TransitionManager.beginDelayedTransition(rootlayout)
                    split3.applyTo(rootlayout)
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        runOnUiThread {
                            glowView.disable()
                            ringing3.visibility = View.GONE
                        }

                    }, 4000)

                    val hh2 = Handler(Looper.getMainLooper())
                    hh2.postDelayed({ ringing3.text = getString(R.string.connected) }, 3000)
                }

                2 -> {
                    runOnUiThread {
                        ringing3.text = ""
                        ringing3.visibility = View.GONE
                        glowView.disable()
                        ringing3.invalidate()
                    }

                    count = 3
                    addTv.text = getString(R.string.remove)
                    TransitionManager.beginDelayedTransition(rootlayout)
                    split4.applyTo(rootlayout)
                }

                3 -> {
                    addTv.text = getString(R.string.remove)
                    count = -2
                    TransitionManager.beginDelayedTransition(rootlayout)
                    split3.applyTo(rootlayout)
                    ringing3.visibility = View.GONE
                }

                -2 -> {
                    addTv.text = getString(R.string.add)
                    count = 1
                    TransitionManager.beginDelayedTransition(rootlayout)
                    initialSet.applyTo(rootlayout)
                }
            }
        }
    }

    var count: Int = 1

    fun addCaller() {
        split3.clone(rootlayout)
        initialSet.clone(rootlayout)
        split3.connect(caller2.id, ConstraintSet.START, caller1.id, ConstraintSet.END, 0)
        split3.connect(caller2.id, ConstraintSet.END, rootlayout.id, ConstraintSet.END, 0)
        split3.connect(caller2.id, ConstraintSet.TOP, caller1.id, ConstraintSet.TOP, 0)
        split3.connect(caller2.id, ConstraintSet.BOTTOM, caller1.id, ConstraintSet.BOTTOM, 0)
        split3.constrainPercentWidth(caller1.id, 0.5f)
        split3.constrainPercentWidth(caller2.id, 0.5f)
        split3.setHorizontalBias(caller1.id, 0f)

        split3.connect(caller3.id, ConstraintSet.START, rootlayout.id, ConstraintSet.START, 0)
        split3.connect(caller3.id, ConstraintSet.END, rootlayout.id, ConstraintSet.END, 0)
        split3.connect(caller3.id, ConstraintSet.TOP, caller1.id, ConstraintSet.BOTTOM, 0)
        split3.connect(caller3.id, ConstraintSet.BOTTOM, rootlayout.id, ConstraintSet.BOTTOM, 0)

        split4.clone(split3)

        split4.connect(caller4.id, ConstraintSet.START, caller3.id, ConstraintSet.END, 0)
        split4.connect(caller4.id, ConstraintSet.END, rootlayout.id, ConstraintSet.END, 0)
        split4.connect(caller4.id, ConstraintSet.TOP, caller1.id, ConstraintSet.BOTTOM, 0)
        split4.connect(caller4.id, ConstraintSet.BOTTOM, rootlayout.id, ConstraintSet.BOTTOM, 0)

        split4.constrainPercentWidth(caller3.id, 0.5f)
        split4.constrainPercentWidth(caller4.id, 0.5f)
        split4.setHorizontalBias(caller3.id, 0f)
    }
}
