package com.example.combatswaps.keybind;

import com.example.combatswaps.CombatSwapsClient;
import com.example.combatswaps.config.CombatSwapsConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CombatSwapsKeybinds {
    // Feature activation keybinds (default: unbound = -1)
    public final KeyBinding attributeSwap = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.attribute_swap",
        -1, // unbound by default
        "category.combatswaps.combat"
    ));

    public final KeyBinding spearSwap = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.spear_swap",
        -1,
        "category.combatswaps.combat"
    ));

    public final KeyBinding pearlCatch = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.pearl_catch",
        -1,
        "category.combatswaps.combat"
    ));

    public final KeyBinding spearMace = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.spear_mace",
        -1,
        "category.combatswaps.combat"
    ));

    public final KeyBinding elytraMace = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.elytra_mace",
        -1,
        "category.combatswaps.combat"
    ));

    public final KeyBinding rocketMace = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.rocket_mace",
        -1,
        "category.combatswaps.combat"
    ));

    // Toggle keybinds (default: unbound = -1)
    public final KeyBinding toggleAttributeSwap = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.toggle_attribute_swap",
        -1,
        "category.combatswaps.toggles"
    ));

    public final KeyBinding toggleSpearSwap = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.toggle_spear_swap",
        -1,
        "category.combatswaps.toggles"
    ));

    public final KeyBinding togglePearlCatch = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.toggle_pearl_catch",
        -1,
        "category.combatswaps.toggles"
    ));

    public final KeyBinding toggleSpearMace = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.toggle_spear_mace",
        -1,
        "category.combatswaps.toggles"
    ));

    public final KeyBinding toggleElytraMace = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.toggle_elytra_mace",
        -1,
        "category.combatswaps.toggles"
    ));

    public final KeyBinding toggleRocketMace = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.combatswaps.toggle_rocket_mace",
        -1,
        "category.combatswaps.toggles"
    ));

    public void handleKeyPresses(net.minecraft.client.MinecraftClient client) {
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;

        // Handle toggle keybinds
        if (toggleAttributeSwap.wasPressed()) {
            config.attributeSwapEnabled = !config.attributeSwapEnabled;
            config.save();
            CombatSwapsClient.sendActionBarMessage(
                "§aCombatSwaps: §rAttribute Swap " + (config.attributeSwapEnabled ? "§aEnabled" : "§cDisabled")
            );
        }

        if (toggleSpearSwap.wasPressed()) {
            config.spearSwapEnabled = !config.spearSwapEnabled;
            config.save();
            CombatSwapsClient.sendActionBarMessage(
                "§aCombatSwaps: §rSpear Swap " + (config.spearSwapEnabled ? "§aEnabled" : "§cDisabled")
            );
        }

        if (togglePearlCatch.wasPressed()) {
            config.pearlCatchEnabled = !config.pearlCatchEnabled;
            config.save();
            CombatSwapsClient.sendActionBarMessage(
                "§aCombatSwaps: §rPearl Catch " + (config.pearlCatchEnabled ? "§aEnabled" : "§cDisabled")
            );
        }

        if (toggleSpearMace.wasPressed()) {
            config.spearMaceEnabled = !config.spearMaceEnabled;
            config.save();
            CombatSwapsClient.sendActionBarMessage(
                "§aCombatSwaps: §rSpear Mace " + (config.spearMaceEnabled ? "§aEnabled" : "§cDisabled")
            );
        }

        if (toggleElytraMace.wasPressed()) {
            config.elytraMaceEnabled = !config.elytraMaceEnabled;
            config.save();
            CombatSwapsClient.sendActionBarMessage(
                "§aCombatSwaps: §rElytra Mace " + (config.elytraMaceEnabled ? "§aEnabled" : "§cDisabled")
            );
        }

        if (toggleRocketMace.wasPressed()) {
            config.rocketMaceEnabled = !config.rocketMaceEnabled;
            config.save();
            CombatSwapsClient.sendActionBarMessage(
                "§aCombatSwaps: §rRocket Mace " + (config.rocketMaceEnabled ? "§aEnabled" : "§cDisabled")
            );
        }
    }
}