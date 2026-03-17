package com.nothinglondon.sdkdemo.demos

import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class GlyphAquariumDemo {

    private val SIZE = 25
    private val RADIUS = 12.37
    private var tick = 0

    val outputFrame = IntArray(SIZE * SIZE)

    private val SPECIES = mapOf(
        "fish" to arrayOf(
            "00004997400000",
            "00499119970990",
            "09991119999110",
            "00499119970990",
            "00004997400000"
        ),
        "shark" to arrayOf(
            "0000000000004000000",
            "0000000000079000000",
            "0000000000499400004",
            "0000000007999900049",
            "0000049999999999999",
            "0049999999999999997",
            "0999911999999999900",
            "9991111199999999997",
            "0999911999999999900",
            "0049999999999999997",
            "0000049999999900049",
            "0000000004999000004",
            "0000000000079000000",
            "0000000000004000000"
        ),
        "ray" to arrayOf(
            "0000479740000000",
            "0047991199740000",
            "0799111119970000",
            "9991111199974411",
            "0799111119970000",
            "0047991199740000",
            "0000479740000000"
        ),
        "smallFish" to arrayOf(
            "0099704",
            "9911991",
            "0099704"
        ),
        "squid" to arrayOf(
            "000000000400007",
            "000000004900490",
            "000499999900994",
            "049999999999999",
            "499999999999111",
            "049999999999999",
            "000499999900994",
            "000000004900490",
            "000000000400007"
        ),
        "crab" to arrayOf(
            "9000009",
            "0900090",
            "0499940",
            "9901099",
            "4999994",
            "0900090",
            "4000004"
        ),
        "hook" to arrayOf(
            "090",
            "090",
            "090",
            "090",
            "099",
            "909",
            "099"
        ),
        "bubbleBig" to arrayOf(
            "04940",
            "49094",
            "90009",
            "49094",
            "04940"
        ),
        "bubbleSmall" to arrayOf(
            "070",
            "707",
            "070"
        )
    )

    data class Decor(val x: Int, val y: Int, val h: Int, val offset: Double)

    private val decor = listOf(
        Decor(3, 22, 3, 0.0), Decor(6, 21, 4, 1.0),
        Decor(9, 23, 2, 2.0), Decor(12, 22, 3, 3.0),
        Decor(15, 21, 4, 0.5), Decor(18, 22, 3, 1.5),
        Decor(21, 23, 2, 2.5)
    )

    data class Bubble(
        var x: Double,
        var y: Double,
        val speed: Double,
        val size: String,
        val wobble: Double
    )

    private data class SceneState(
        var type: String = "wait",
        var timer: Int = 10,
        var posX: Double = 30.0,
        var posY: Double = 10.0,
        var angle: Double = 0.0,
        var hookY: Double = -10.0,
        var fishPhase: Int = 0,
        var bubbles: MutableList<Bubble> = mutableListOf()
    )

    private var scene = SceneState()

    private fun initEvent(type: String) {
        scene.type = type
        scene.posX = 30.0
        scene.timer = 0
        scene.angle = 0.0
        when (type) {
            "hook" -> {
                scene.hookY = -15.0
                scene.fishPhase = 0
                scene.posX = 34.0
                scene.posY = 14.0
            }
            "shark" -> scene.posY = 7.0
            "ray"   -> scene.posY = 9.0
            "school" -> scene.posY = 5.0
            "squid" -> scene.posY = 9.0
            "fish"  -> scene.posY = 13.0
            "idle"  -> {
                scene.bubbles = MutableList(4) {
                    Bubble(
                        x = 5.0 + Random.nextDouble() * 15.0,
                        y = 25.0 + Random.nextDouble() * 20.0,
                        speed = 0.1 + Random.nextDouble() * 0.1,
                        size = if (Random.nextBoolean()) "big" else "small",
                        wobble = Random.nextDouble() * Math.PI * 2
                    )
                }
                scene.timer = 0
            }
        }
    }

    private fun endEvent() {
        scene.type = "wait"
        scene.timer = (30 + Random.nextDouble() * 60).toInt()
    }

    private fun updateLogic() {
        if (scene.type == "wait") {
            scene.timer--
            if (scene.timer <= 0) {
                val r = Random.nextDouble()
                initEvent(when {
                    r < 0.10 -> "hook"
                    r < 0.25 -> "shark"
                    r < 0.45 -> "ray"
                    r < 0.60 -> "school"
                    r < 0.75 -> "crab"
                    r < 0.85 -> "squid"
                    r < 0.92 -> "idle"
                    else     -> "fish"
                })
            }
        } else {
            scene.timer++
            when (scene.type) {
                "hook" -> {
                    if (scene.fishPhase == 0) {
                        if (scene.hookY < 12) scene.hookY += 0.25
                        if (scene.posX > 18) scene.posX -= 0.35
                        if (scene.hookY >= 12 && scene.posX <= 18) {
                            scene.fishPhase = 1
                            scene.timer = 0
                        }
                    } else if (scene.fishPhase == 1) {
                        if (scene.timer > 40) scene.fishPhase = 2
                    } else {
                        scene.posX += 0.7
                        scene.hookY -= 0.5
                        if (scene.posX > 35) endEvent()
                    }
                }
                "shark" -> {
                    scene.posX -= 0.40
                    if (scene.posX < -25) endEvent()
                }
                "squid" -> {
                    scene.posX -= if (scene.timer % 30 < 8) 0.8 else 0.15
                    if (scene.posX < -20) endEvent()
                }
                "crab" -> {
                    val progress = scene.timer / 200.0
                    scene.angle = progress * -Math.PI
                    scene.posX = 12 + cos(scene.angle) * 11
                    scene.posY = 12 + sin(scene.angle) * 11
                    if (scene.timer > 200) endEvent()
                }
                "idle" -> {
                    scene.bubbles.forEach { b ->
                        b.y -= b.speed
                        if (b.y < -10) {
                            b.y = 25.0 + Random.nextDouble() * 15
                            b.x = 5.0 + Random.nextDouble() * 15
                        }
                    }
                    if (scene.timer > 300) endEvent()
                }
                else -> {
                    scene.posX -= 0.25
                    if (scene.posX < -20) endEvent()
                }
            }
        }
    }

    // Animation modifier bag — mirrors the JS mods object
    private data class ShapeMods(
        val wag: Double = 0.0,
        val wagLength: Int = 4,
        val flap: Double = 0.0,
        val pulse: Double = 0.0,
        val scuttle: Int = 0,
        val flipX: Boolean = false,
        val rotation: Double? = null
    )

    fun renderNextFrame(): IntArray {
        outputFrame.fill(0)
        tick++
        updateLogic()

        // 1. Draw seaweed
        for (d in decor) {
            val sway = sin(tick * 0.05 + d.offset) * 0.8
            for (i in 0 until d.h) {
                val bend = (i.toDouble() / d.h) * sway
                val tx = (d.x + bend).toInt()
                val ty = (SIZE - 1) - i
                if (tx in 0 until SIZE && ty in 0 until SIZE) {
                    outputFrame[ty * SIZE + tx] = 80 + (i * 15)
                }
            }
        }

        // 2. Draw scene
        if (scene.type == "wait") return outputFrame

        val tailWag = sin(tick * 0.3) * 0.7
        val wingFlap = sin(tick * 0.1) * 1.2

        when (scene.type) {
            "idle" -> {
                scene.bubbles.forEach { b ->
                    val bx = b.x + sin(tick * 0.05 + b.wobble) * 2.0
                    val sprite = if (b.size == "big") SPECIES["bubbleBig"]!! else SPECIES["bubbleSmall"]!!
                    drawShape(sprite, bx, b.y, ShapeMods())
                }
            }
            "school" -> {
                listOf(
                    Pair(scene.posX,      scene.posY),
                    Pair(scene.posX + 8,  scene.posY + 3),
                    Pair(scene.posX + 4,  scene.posY + 7),
                    Pair(scene.posX + 10, scene.posY + 10)
                ).forEachIndexed { i, f ->
                    val wag = sin(tick * 0.3 + i) * 0.4
                    drawShape(SPECIES["smallFish"]!!, f.first, f.second + wag,
                        ShapeMods(wag = wag, wagLength = 2))
                }
            }
            "ray" -> drawShape(SPECIES["ray"]!!, scene.posX, scene.posY,
                ShapeMods(flap = wingFlap, wag = tailWag * 0.5, wagLength = 6))
            "shark" -> drawShape(SPECIES["shark"]!!, scene.posX, scene.posY,
                ShapeMods(wag = tailWag * 1.5, wagLength = 7))
            "squid" -> {
                val pulse = if (scene.timer % 30 < 8) 0.0 else 0.6
                drawShape(SPECIES["squid"]!!, scene.posX, scene.posY, ShapeMods(pulse = pulse))
            }
            "crab" -> {
                val scuttle = if (scene.timer % 6 < 3) 1 else 0
                drawShape(SPECIES["crab"]!!, scene.posX, scene.posY,
                    ShapeMods(scuttle = scuttle, rotation = scene.angle - Math.PI / 2))
            }
            "hook" -> {
                // Fishing line from top to hook
                val stringEndY = (scene.hookY - 3.5).toInt()
                for (y in 0..stringEndY) {
                    val dist = sqrt((12 - 12.0) * (12 - 12.0) + (y - 12.0) * (y - 12.0))
                    if (dist <= RADIUS) {
                        val idx = y * SIZE + 12
                        if (idx in outputFrame.indices) outputFrame[idx] = maxOf(outputFrame[idx], 200)
                    }
                }
                drawShape(SPECIES["hook"]!!, 12.0, scene.hookY, ShapeMods())
                val isFlipped = scene.fishPhase == 2
                val swimWag = if (scene.fishPhase == 1) 0.0 else tailWag * if (isFlipped) 2.0 else 1.0
                drawShape(SPECIES["fish"]!!, scene.posX, scene.posY,
                    ShapeMods(flipX = isFlipped, wag = swimWag, wagLength = 3))
            }
            "fish" -> drawShape(SPECIES["fish"]!!, scene.posX, scene.posY,
                ShapeMods(wag = tailWag, wagLength = 4))
            else -> SPECIES[scene.type]?.let {
                drawShape(it, scene.posX, scene.posY, ShapeMods(wag = tailWag, wagLength = 4))
            }
        }

        return outputFrame
    }

    private fun drawShape(shape: Array<String>, ox: Double, oy: Double, mods: ShapeMods) {
        val srcW = shape[0].length
        val srcH = shape.size
        val cx = srcW / 2.0
        val cy = srcH / 2.0

        for (srcY in 0 until srcH) {
            for (rawSrcX in 0 until srcW) {
                val srcX = if (mods.flipX) (srcW - 1 - rawSrcX) else rawSrcX
                val char = shape[srcY][srcX]
                if (char == '0') continue

                var py = srcY.toDouble()
                val spriteX = if (mods.flipX) (srcW - 1 - srcX) else srcX

                // Tail wag — bends the rear portion of the sprite
                if (mods.wag != 0.0 && spriteX >= srcW - mods.wagLength) {
                    val tailFactor = (spriteX - (srcW - mods.wagLength)).toDouble() / mods.wagLength
                    py += round(mods.wag * tailFactor * 2)
                }

                // Wing flap — displaces top/bottom rows (ray wings)
                if (mods.flap != 0.0 && (srcY < 2 || srcY > srcH - 3)) {
                    val intensity = if (srcY < 2) (2 - srcY).toDouble() else (srcY - (srcH - 3)).toDouble()
                    py += if (srcY < 2) -round(mods.flap * intensity) else round(mods.flap * intensity)
                }

                // Tentacle pulse — pinches squid tentacles inward while drifting
                if (mods.pulse != 0.0 && spriteX > srcW / 2) {
                    val centerDiff = srcY - srcH / 2.0
                    py -= round(centerDiff * mods.pulse)
                }

                // Crab scuttle — lifts bottom row on alternate frames
                if (mods.scuttle != 0 && srcY >= srcH - 1) {
                    py -= mods.scuttle
                }

                // Rotation (crab walking the perimeter) or standard placement
                val tx: Int
                val ty: Int
                if (mods.rotation != null) {
                    val rx = rawSrcX - cx
                    val ry = py - cy
                    tx = (ox + rx * cos(mods.rotation) - ry * sin(mods.rotation)).toInt()
                    ty = (oy + rx * sin(mods.rotation) + ry * cos(mods.rotation)).toInt()
                } else {
                    tx = (ox + rawSrcX - cx).toInt()
                    ty = (oy + py - cy).toInt()
                }

                if (tx in 0 until SIZE && ty in 0 until SIZE) {
                    val dist = sqrt((tx - 12.0) * (tx - 12.0) + (ty - 12.0) * (ty - 12.0))
                    if (dist <= RADIUS) {
                        val bValue = if (char == '1') 40 else Character.getNumericValue(char) * 28
                        val idx = ty * SIZE + tx
                        if (bValue > outputFrame[idx]) outputFrame[idx] = bValue
                    }
                }
            }
        }
    }
}
