# Bundle In Creative Mod for Minecraft 1.20.1 (Forge)

This mod adds the vanilla Bundle item to the Tools & Utilities creative tab, making it visible in:
- Creative Menu
- EMI/JEI recipe viewers
- FTB Quests

## Configuration

Open the Mods list in-game (Configured or the Forge Mod Menu button) and use the **Bundle In Creative Settings** screen to:
- Toggle whether the Bundle appears in a creative tab
- Choose which creative tab should host the Bundle
- Pick which bundle recipe(s) should be available: Off, Rabbit Hide, Leather, or Both

These options are also available through the generated `bundleincreative-common.toml` config file if you prefer manual editing.

## How to Build

1. Open a terminal/command prompt in this directory
2. Run the appropriate Gradle command:
   - Windows: `gradlew.bat build`
   - Linux/Mac: `./gradlew build`
3. The compiled mod JAR will be in `build/libs/`

## Installation

1. Build the mod using the instructions above
2. Copy the JAR file from `build/libs/` to your Minecraft `mods` folder
3. Launch Minecraft with Forge 1.20.1

## Requirements

- Minecraft 1.20.1
- Forge 47.3.0 or higher
- Java 17

## License

MIT License - Feel free to use and modify as needed.
