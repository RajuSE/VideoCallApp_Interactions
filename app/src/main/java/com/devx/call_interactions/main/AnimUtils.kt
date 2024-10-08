package com.devx.call_interactions.main

import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation

object AnimUtils {
    private var animTimes = 0

    fun drop(v: View) {
        val anim: Animation = ScaleAnimation(
            1f, 0f,
            1f, 0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.fillAfter = true
        anim.duration = 200

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                v.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })

        v.startAnimation(anim)
    }


    fun popup(v: View) {
        val anim: Animation = ScaleAnimation(
            0f, 1f,
            0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.fillAfter = true
        anim.duration = 200
        v.startAnimation(anim)
    }

    fun pan(v: View) {
        val anim: Animation = ScaleAnimation(
            1f, 0.8f,
            1f, 0.8f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.fillAfter = true
        anim.duration = 200

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                val anim1: Animation = ScaleAnimation(
                    0.8f, 1f,
                    0.8f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                )
                anim1.fillAfter = true
                anim1.duration = 200
                v.startAnimation(anim1)
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })

        v.startAnimation(anim)
    }

    fun fall(v: View) {
        val anim: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f
        )
        anim.fillAfter = true
        anim.duration = 100
        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(anim)
    }

    fun shake(v: View, listener: CallerScreenListener) {
        val anim: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0.1f,
            Animation.RELATIVE_TO_SELF, -1f,
            Animation.RELATIVE_TO_SELF, -1f
        )
        anim.fillAfter = true
        anim.duration = 100

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                val anim1: Animation = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.1f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, -1f,
                    Animation.RELATIVE_TO_SELF, -1f
                )
                anim1.fillAfter = true
                anim1.duration = 100
                v.startAnimation(anim1)

                if (animTimes < 3) {
                    shake(v, listener)
                    animTimes++
                } else {
                    animTimes = 0
                    listener.shaked()
                    return
                }
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })

        v.startAnimation(anim)
    }

    fun animateUpSlow(v: View, listener: CallerScreenListener?, speed: Int) {
        val anim: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, -1f
        )
        anim.fillAfter = true
        anim.duration = speed.toLong()
        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                listener?.reachedUp()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(anim)
    }

    fun animateDownSlow(v: View, listener: CallerScreenListener?, speed: Int) {
        val anim: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, -1f,
            Animation.RELATIVE_TO_SELF, 0f
        )
        anim.fillAfter = true
        anim.duration = speed.toLong()
        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                listener?.reachedDown()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(anim)
    }

    fun animateBottomHide(v: View, listener: CallerScreenListener?, speed: Int) {
        val anim: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f
        )
        anim.fillAfter = true
        anim.duration = speed.toLong()
        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                listener?.reachedUp()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(anim)
    }

    fun animateBottomShow(v: View, listener: CallerScreenListener?, speed: Int) {
        val anim: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, 0f
        )
        anim.fillAfter = true
        anim.duration = speed.toLong()
        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                listener?.reachedUp()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(anim)
    }
}
