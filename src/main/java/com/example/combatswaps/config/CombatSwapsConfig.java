package com.example.combatswaps.config;

import com.example.combatswaps.CombatSwapsClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CombatSwapsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("combatswaps.json");

    // Feature toggles
    public boolean attributeSwapEnabled = true;
    public boolean spearSwapEnabled = true;
    public boolean pearlCatchEnabled = true;
    public boolean spearMaceEnabled = true;
    public boolean elytraMaceEnabled = true;
    public boolean rocketMaceEnabled = true;

    // Hotbar slots (0-8, where 0 is first slot)
    public int attributeSwapSlot = 1;        // Slot for weapon (e.g., sword)
    public int spearSlot = 2;                 // Slot for spear/trident
    public int pearlSlot = 3;                 // Slot for ender pearl
    public int pearlCatchSecondSlot = 4;      // Slot for wind charge or mace
    public int maceSlot = 5;                  // Slot for mace
    public int rocketSlot = 6;                // Slot for firework rocket

    // Per-step delays in ticks (default 1 tick)
    public int swapDelay = 1;                 // Delay after swapping slot
    public int useDelay = 1;                  // Delay after using item
    public int attackDelay = 1;               // Delay after attacking
    public int pearlThrowDelay = 1;           // Delay after throwing pearl
    public int secondItemDelay = 1;           // Delay after using second item in pearl catch

    public static CombatSwapsConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                CombatSwapsConfig config = GSON.fromJson(reader, CombatSwapsConfig.class);
                if (config != null) {
                    CombatSwapsClient.LOGGER.info("Loaded CombatSwaps config from {}", CONFIG_PATH);
                    return config;
                }
            } catch (Exception e) {
                CombatSwapsClient.LOGGER.error("Failed to load CombatSwaps config, using defaults", e);
            }
        }
        CombatSwapsConfig defaultConfig = new CombatSwapsConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
                CombatSwapsClient.LOGGER.info("Saved CombatSwaps config to {}", CONFIG_PATH);
            }
        } catch (IOException e) {
            CombatSwapsClient.LOGGER.error("Failed to save CombatSwaps config", e);
        }
    }

    // Validation helpers
    public int clampSlot(int slot) {
        return Math.max(0, Math.min(8, slot));
    }

    public int clampDelay(int delay) {
        return Math.max(0, delay);
    }

    public void validateAndClamp() {
        attributeSwapSlot = clampSlot(attributeSwapSlot);
        spearSlot = clampSlot(spearSlot);
        pearlSlot = clampSlot(pearlSlot);
        pearlCatchSecondSlot = clampSlot(pearlCatchSecondSlot);
        maceSlot = clampSlot(maceSlot);
        rocketSlot = clampSlot(rocketSlot);

        swapDelay = clampDelay(swapDelay);
        useDelay = clampDelay(useDelay);
        attackDelay = clampDelay(attackDelay);
        pearlThrowDelay = clampDelay(pearlThrowDelay);
        secondItemDelay = clampDelay(secondItemDelay);
    }
}