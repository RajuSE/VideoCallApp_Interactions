package com.devx.call_interactions.main

interface CallerScreenListener {
    fun reachedUp()
    fun reachedDown()
    fun answered()
    fun released()
    fun clickedAnswer()
    fun shaked()
}
