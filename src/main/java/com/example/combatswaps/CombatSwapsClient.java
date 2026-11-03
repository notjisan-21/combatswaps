package com.example.combatswaps;

import com.example.combatswaps.config.CombatSwapsConfig;
import com.example.combatswaps.keybind.CombatSwapsKeybinds;
import com.example.combatswaps.sequence.SequenceManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatSwapsClient implements ClientModInitializer {
    public static final String MOD_ID = "combatswaps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static CombatSwapsConfig CONFIG;
    public static CombatSwapsKeybinds KEYBINDS;
    public static SequenceManager SEQUENCE_MANAGER;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing CombatSwaps...");

        // Load config
        CONFIG = CombatSwapsConfig.load();

        // Initialize keybinds
        KEYBINDS = new CombatSwapsKeybinds();
        KeyBindingHelper.registerKeyBinding(KEYBINDS.attributeSwap);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.spearSwap);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.pearlCatch);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.spearMace);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.elytraMace);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.rocketMace);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.toggleAttributeSwap);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.toggleSpearSwap);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.togglePearlCatch);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.toggleSpearMace);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.toggleElytraMace);
        KeyBindingHelper.registerKeyBinding(KEYBINDS.toggleRocketMace);

        // Initialize sequence manager
        SEQUENCE_MANAGER = new SequenceManager();

        // Register tick handler
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                SEQUENCE_MANAGER.tick(client);
                KEYBINDS.handleKeyPresses(client);
            }
        });

        LOGGER.info("CombatSwaps initialized successfully!");
    }

    public static void sendActionBarMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), true);
        }
    }
}