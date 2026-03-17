package com.nothinglondon.sdkdemo.demos.animation

import android.content.Context
import com.nothing.ketchum.GlyphMatrixManager
import com.nothinglondon.sdkdemo.demos.GlyphMatrixService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

class AnimationDemoService : GlyphMatrixService("Glyph-Aquarium") {

    private val backgroundScope = CoroutineScope(Dispatchers.IO)
    private val uiScope = CoroutineScope(Dispatchers.Main)
    
    // Create our Aquarium Engine
    private val aquarium = com.nothinglondon.sdkdemo.demos.GlyphAquariumDemo()

    override fun performOnServiceConnected(
        context: Context,
        glyphMatrixManager: GlyphMatrixManager
    ) {
        backgroundScope.launch {
            while (isActive) {
                // Get the calculated aquarium frame
                val array = aquarium.renderNextFrame()
                
                uiScope.launch {
                    glyphMatrixManager.setMatrixFrame(array)
                }
                
                // 30ms delay is roughly 33fps
                delay(30)
            }
        }
    }

    override fun performOnServiceDisconnected(context: Context) {
        backgroundScope.cancel()
    }
}