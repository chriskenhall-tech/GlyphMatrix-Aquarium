# Glyph Aquarium

A Glyph Toy for Nothing Phone that turns the Glyph Matrix into a living pixel aquarium. Fish, sharks, rays, squids, crabs, and more swim across the 25×25 LED matrix in smooth animation — complete with swaying seaweed, rising bubbles, and a fishing hook scene.

Built on top of the [Nothing Glyph Matrix SDK](https://github.com/Nothing-Developer-Programme/GlyphMatrix-Developer-Kit), this project also serves as a working reference for the `GlyphMatrixService` wrapper pattern and direct `setMatrixFrame()` rendering.

---

## Demos included

| Demo | Description |
|---|---|
| **Glyph Aquarium** | Animated aquarium — randomised scenes cycle through fish, shark, manta ray, squid, school of fish, crab, fishing hook, and idle bubbles |
| **Basic** | Displays the app icon; touch-point increments a counter |
| **Glyph Button** | Generates a new random LED pattern on each long-press |

> **Tip:** Short-press the Glyph Button on the back of the phone to cycle between active toys.

---

## Features

- Smooth pixel animation at ~33 fps via direct `setMatrixFrame(IntArray)` calls
- Per-frame brightness normalisation — brightest pixel always at 100% output
- Multi-level shading (1–9 scale) mapped to a compressed 130–255 brightness range so all detail remains visible
- Seaweed sway, tail wag, wing flap, tentacle pulse, and crab scuttle deformation effects
- Always-on Display (AOD) support declared for all toys
- Custom pixel-art launcher icon matching the aquarium theme

---

## Requirements

- Android Studio (Hedgehog or newer)
- Kotlin
- Nothing Phone with Glyph Matrix (Phone 3a or later)
- USB debugging enabled

---

## Setup

**1.** Connect your Nothing Phone via USB with USB debugging enabled.

**2.** Clone this repository:
```
git clone https://github.com/Nothing-Developer-Programme/GlyphMatrix-Example-Project.git
```

**3.** Open Android Studio → **File → Open** → select the cloned folder.

**4.** Wait for Gradle sync to complete, then click **Run** (▶) with your device selected.

Alternatively, from the command line:
```
./gradlew installDebug
```

---

## Activating a Toy

Once the app is installed, toys must be enabled in the Nothing X app before they appear on the Glyph Matrix.

<table>
<tr>
<td width="60%" valign="top">

**1.** Open **Nothing X** on your phone and go to **Glyph Interface**.

**2.** Tap the button on the right to manage toys.

</td>
<td width="40%" align="center">
<img src="images/toy_carousoul.png" alt="Toy carousel" style="max-height: 300px;">
</td>
</tr>

<tr>
<td width="60%" valign="top">

**3.** Drag a toy from **Disabled** to **Active** using the handle bars.

</td>
<td width="40%" align="center">
<img src="images/toy_disable.png" alt="Moving toys" style="max-height: 300px;">
</td>
</tr>

<tr>
<td width="60%" valign="top">

**4.** The toy is now active. Press the Glyph Button on the back of the phone to trigger it.

</td>
<td width="40%" align="center">
<img src="images/toy_active.png" alt="Active toys" style="max-height: 300px;">
</td>
</tr>
</table>

---

## Always-on Display

All three toys support AOD. To enable:

1. Open **Nothing X → Glyph → Always on Display**
2. Select **Glyph Aquarium** (or another toy) as your AOD

The aquarium will animate on the Glyph Matrix whenever the screen is off.

---

## Project structure

```
app/src/main/java/com/nothinglondon/sdkdemo/
├── demos/
│   ├── GlyphMatrixService.kt       — abstract base, handles SDK lifecycle
│   ├── GlyphAquariumDemo.kt        — aquarium engine (sprites, physics, rendering)
│   ├── animation/
│   │   └── AnimationDemoService.kt — runs the aquarium at ~33 fps
│   ├── basic/
│   │   └── BasicDemoService.kt     — icon display + counter
│   └── glyphbutton/
│       └── GlyphButtonDemoService.kt — random pattern on long-press
└── MainActivity.kt
```

---

## Debugging

Enable the Glyph debug flag before running:
```
adb shell settings put global nt_glyph_interface_debug_enable 1
```

---

## License

MIT — see [LICENSE](LICENSE).
