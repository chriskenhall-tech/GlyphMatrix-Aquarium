# Glyph Aquarium 🐠

> *Your phone's back is now an aquarium.*

A Glyph Toy for Nothing Phone that transforms the 25×25 LED matrix into a living, breathing pixel ocean. A cast of sea creatures swim, dart, glide, and lurk across the glowing dots — each with their own personality and movement style.

Tap the Glyph Button and you might get a lazy fish on its afternoon swim, a great white slicing through the dark, a manta ray banking on thermal currents, a school of four fish moving in tight formation, a squid pulsing its tentacles, a crab scuttling along the edge of the matrix, an idle scene of bubbles drifting upward — or a poor fish discovering a fishing hook the hard way.

Every press is a surprise.

Built on the [Nothing Glyph Matrix SDK](https://github.com/Nothing-Developer-Programme/GlyphMatrix-Developer-Kit). Also works as a practical reference for direct `setMatrixFrame()` rendering and the `GlyphMatrixService` wrapper pattern.

---

## What's swimming in there

| Creature | What it does |
|---|---|
| 🐟 **Fish** | A single fish making a leisurely pass across the matrix, tail wagging |
| 🦈 **Shark** | Bigger, faster, with a more aggressive tail swing |
| 🐦 **Manta Ray** | Sweeps through with slow, graceful wing flaps |
| 🐟🐟 **School** | Four small fish swimming in diamond formation — they move together |
| 🦑 **Squid** | Drifts and pulses its tentacles in rhythmic jets |
| 🦀 **Crab** | Scuttles sideways along the circular edge of the matrix |
| 🎣 **Hook** | A line drops from the top, a fish investigates... and gets caught |
| 🫧 **Idle** | Bubbles wobble upward through swaying seaweed |

Scenes are randomly selected and loop continuously until you press the button again.

---

## Features

- ~33 fps smooth animation via direct `setMatrixFrame(IntArray)` calls
- Per-frame brightness normalisation — the brightest pixel always hits 100% output, every frame
- 9-level sprite shading mapped to a compressed 130–255 brightness range so no detail gets lost on the LEDs
- Physics deformations per creature: tail wag, wing flap, tentacle pulse, crab scuttle, rotation
- Swaying seaweed on every frame using sine-based deformation
- Always-on Display (AOD) support — the aquarium keeps swimming when your screen is off
- Custom pixel-art launcher icon: a still from the school-of-fish scene

---

## Requirements

- Android Studio (Hedgehog or newer)
- Kotlin
- Nothing Phone with Glyph Matrix (Phone 3a or later)
- USB debugging enabled

---

## Setup

**1.** Connect your Nothing Phone via USB and enable USB debugging.

**2.** Enable the Glyph debug flag:
```bash
adb shell settings put global nt_glyph_interface_debug_enable 1
```

**3.** Clone this repository:
```bash
git clone https://github.com/Nothing-Developer-Programme/GlyphMatrix-Example-Project.git
```

**4.** Open Android Studio → **File → Open** → select the folder, wait for Gradle sync, then hit **Run ▶**.

Or from the terminal:
```bash
./gradlew installDebug
```

---

## Activating a toy

Once installed, toys need to be enabled in Nothing X before they'll respond to the Glyph Button.

<table>
<tr>
<td width="60%" valign="top">

**1.** Open **Nothing X** on your phone → **Glyph Interface**.

**2.** Tap the manage button on the right.

</td>
<td width="40%" align="center">
<img src="images/toy_carousoul.png" alt="Toy carousel" style="max-height: 300px;">
</td>
</tr>

<tr>
<td width="60%" valign="top">

**3.** Drag **Glyph Aquarium** from Disabled → Active.

</td>
<td width="40%" align="center">
<img src="images/toy_disable.png" alt="Moving toys" style="max-height: 300px;">
</td>
</tr>

<tr>
<td width="60%" valign="top">

**4.** Press the Glyph Button on the back of the phone. Something will swim.

</td>
<td width="40%" align="center">
<img src="images/toy_active.png" alt="Active toys" style="max-height: 300px;">
</td>
</tr>
</table>

> **Tip:** Short-press the Glyph Button to cycle between active toys.

---

## Always-on Display

The aquarium also runs when your screen is off.

1. Open **Nothing X → Glyph → Always on Display**
2. Select **Glyph Aquarium**

Now your phone glows with a living ocean scene on the lock screen — no interaction needed.

---

## Other toys in this project

| Toy | Description |
|---|---|
| **Basic** | Shows the app icon on the matrix; touch-point increments a counter |
| **Glyph Button** | Generates a new random dot pattern on each long-press |

These are simpler examples showing the SDK's `GlyphMatrixFrame` builder API, useful if you're learning the basics before diving into raw frame rendering.

---

## How it works

The aquarium is a pure Kotlin rendering engine (`GlyphAquariumDemo.kt`) that writes directly into a 625-element `IntArray` (25×25) and calls `glyphMatrixManager.setMatrixFrame()` at ~33 fps.

Sprites are stored as arrays of strings where each character is a brightness digit (0–9). The renderer applies deformation passes (wag, flap, pulse, scuttle, rotation) before mapping each pixel into the circular glyph matrix bounds. A final normalisation pass scales the frame so the peak pixel always reaches 255.

```
app/src/main/java/com/nothinglondon/sdkdemo/
├── demos/
│   ├── GlyphMatrixService.kt         abstract base — SDK lifecycle, touch events
│   ├── GlyphAquariumDemo.kt          aquarium engine — sprites, scene logic, rendering
│   ├── animation/
│   │   └── AnimationDemoService.kt   drives the aquarium loop at ~33 fps
│   ├── basic/
│   │   └── BasicDemoService.kt       icon + counter example
│   └── glyphbutton/
│       └── GlyphButtonDemoService.kt random pattern on long-press
└── MainActivity.kt
```

---

## License

MIT — see [LICENSE](LICENSE).
