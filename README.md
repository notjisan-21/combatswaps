# CombatSwaps

A client-side Fabric mod for Minecraft 1.21.11 that adds configurable hotbar swap sequences for combat.

## Features

| Feature | Description | Default Key |
|---------|-------------|-------------|
| **Attribute Swap** | Swap to weapon slot, attack targeted entity, swap back | Unbound |
| **Spear Swap** | Swap to spear/trident slot, use/attack, swap back | Unbound |
| **Pearl Catch** | Throw ender pearl, swap to wind charge/mace, use it, swap back | Unbound |
| **Spear Mace** | Use spear, swap to mace, attack, swap back | Unbound |
| **Elytra Mace** | While gliding: swap to mace, attack, swap back | Unbound |
| **Rocket Mace** | While gliding: use firework rocket, swap to mace, attack, swap back | Unbound |

Each feature has its own toggle keybind (also unbound by default).

## Requirements

- Minecraft 1.21.11
- Fabric Loader 0.18.4+
- Fabric API 0.141.6+
- Java 21

## Building Locally

### Prerequisites

- JDK 21 (Temurin/OpenJDK recommended)
- Git

### Steps

```bash
# Clone the repository
git clone https://github.com/yourname/combatswaps.git
cd combatswaps

# Build the mod
./gradlew build

# The JAR will be in build/libs/
ls build/libs/
```

## Building via GitHub Actions

1. Fork or create a new repository on GitHub
2. Upload all files from this project to the repository
3. Go to the **Actions** tab in your GitHub repository
4. Enable workflows if prompted
5. Run the "Build CombatSwaps" workflow manually (or push to trigger)
6. Download the `combatswaps-jar` artifact from the workflow run
7. Place the JAR in your Minecraft `mods` folder

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft 1.21.11
2. Install [Fabric API](https://modrinth.com/mod/fabric-api/versions) for 1.21.11
3. Place `combatswaps-1.0.0.jar` in your `.minecraft/mods` folder
4. Launch Minecraft with the Fabric profile

## Configuration

### In-Game (ModMenu)

If you have [ModMenu](https://modrinth.com/mod/modmenu) installed:
1. Open ModMenu from the main menu
2. Find "CombatSwaps" in the list
3. Click the config button (gear icon)
4. Adjust slots, delays, and enable/disable features

### Config File

Config is saved at `.minecraft/config/combatswaps.json`:

```json
{
  "attributeSwapEnabled": true,
  "spearSwapEnabled": true,
  "pearlCatchEnabled": true,
  "spearMaceEnabled": true,
  "elytraMaceEnabled": true,
  "rocketMaceEnabled": true,
  "attributeSwapSlot": 1,
  "spearSlot": 2,
  "pearlSlot": 3,
  "pearlCatchSecondSlot": 4,
  "maceSlot": 5,
  "rocketSlot": 6,
  "swapDelay": 1,
  "useDelay": 1,
  "attackDelay": 1,
  "pearlThrowDelay": 1,
  "secondItemDelay": 1
}
```

### Slot Numbers

Slots are 0-indexed (0 = first hotbar slot, 8 = ninth hotbar slot).

### Delays

All delays are in **ticks** (1 tick = 1/20 second = 50ms). Default is 1 tick.

## Keybinds

All keybinds are **unbound by default**. Set them in Controls → Combat Swaps / Combat Swaps Toggles:

### Activation Keys
- Attribute Swap
- Spear Swap
- Pearl Catch
- Spear Mace
- Elytra Mace
- Rocket Mace

### Toggle Keys
- Toggle Attribute Swap
- Toggle Spear Swap
- Toggle Pearl Catch
- Toggle Spear Mace
- Toggle Elytra Mace
- Toggle Rocket Mace

When toggling a feature, an action bar message appears showing the new state.

## How It Works

- Sequences run as a **tick-based state machine** using `ClientTickEvents.END_CLIENT_TICK`
- Uses only vanilla client actions: slot changes, `interactItem`, `attackEntity`
- No custom packets, no thread sleeping, no aim assist
- Safely cancels if: screen opens, player dies, world unloads, required item missing
- Always restores original hotbar slot on completion or cancellation

## Compatibility

- ✅ VulkanMod
- ✅ Sodium
- ✅ Iris
- ✅ ModMenu (optional, for config UI)

No rendering mixins or hooks — uses Fabric API events only.

## License

MIT License — see [LICENSE](LICENSE) for details.

## Adjustments You May Need

| Setting | Default | Adjust If... |
|---------|---------|--------------|
| `attributeSwapSlot` | 1 (slot 2) | Your weapon is in a different slot |
| `spearSlot` | 2 (slot 3) | Your trident is in a different slot |
| `pearlSlot` | 3 (slot 4) | Your ender pearls are elsewhere |
| `pearlCatchSecondSlot` | 4 (slot 5) | Wind charge/mace is in different slot |
| `maceSlot` | 5 (slot 6) | Your mace is in a different slot |
| `rocketSlot` | 6 (slot 7) | Firework rockets are elsewhere |
| All delays | 1 tick | You experience timing issues (increase) |
| Keybinds | Unbound | Set to your preferred keys in Controls menu |

## Credits

Built with Fabric Loom 1.15, Fabric API 0.141.6, Yarn mappings 1.21.11+build.1.