package com.devx.call_interactions.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.devx.call_interactions.R
import com.devx.call_interactions.main.views.CallAnswerView

class MainActivity : AppCompatActivity(), CallerScreenListener {
    private var answer: View? = null
    private var answerViewLayout: CallAnswerView? = null
    private var picked = false

    lateinit var r1: Runnable
    lateinit var r2: Runnable
    lateinit var r3: Runnable
    lateinit var h: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        answer = findViewById(R.id.answer_view)
        answerViewLayout = findViewById(R.id.picker_layout)

        answerViewLayout?.setListener(this)

        r1 = Runnable { AnimUtils.animateUpSlow(answer!!, this@MainActivity, 1000) }
        r2 = Runnable { AnimUtils.shake(answer!!, this@MainActivity) }
        r3 = Runnable { AnimUtils.animateDownSlow(answer!!, this@MainActivity, 800) }

        h = Handler(Looper.getMainLooper())
        h.post(r1)
    }

    override fun reachedUp() {
        h.post(r2)
    }

    override fun reachedDown() {
        h.post(r1)
    }

    override fun answered() {
        picked = true
    }

    override fun released() {
        answerViewLayout!!.moveToBottom()
        if (picked) {
            picked = false
            val i = Intent(this@MainActivity, CallPickedActivity::class.java)
            startActivity(i)
        }
    }

    override fun clickedAnswer() {
    }

    override fun shaked() {
        h.post(r3)
    }
}
