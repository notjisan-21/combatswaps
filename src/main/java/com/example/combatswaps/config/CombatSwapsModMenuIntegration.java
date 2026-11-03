package com.example.combatswaps.config;

import com.example.combatswaps.CombatSwapsClient;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class CombatSwapsModMenuIntegration {
    public static void registerModMenuConfig() {
        try {
            // Register with AutoConfig (ModMenu integration)
            AutoConfig.register(CombatSwapsConfig.class, GsonConfigSerializer::new);
            CombatSwapsClient.LOGGER.info("ModMenu integration registered");
        } catch (Exception e) {
            CombatSwapsClient.LOGGER.warn("ModMenu integration failed (mod not present or API changed): {}", e.getMessage());
        }
    }

    // This class is used by AutoConfig to generate the config screen
    @Config(name = "combatswaps")
    public static class CombatSwapsConfigScreen implements ConfigData {
        @ConfigEntry.Category("general")
        @ConfigEntry.Gui.RequiresRestart
        public boolean attributeSwapEnabled = true;

        @ConfigEntry.Category("general")
        @ConfigEntry.Gui.RequiresRestart
        public boolean spearSwapEnabled = true;

        @ConfigEntry.Category("general")
        @ConfigEntry.Gui.RequiresRestart
        public boolean pearlCatchEnabled = true;

        @ConfigEntry.Category("general")
        @ConfigEntry.Gui.RequiresRestart
        public boolean spearMaceEnabled = true;

        @ConfigEntry.Category("general")
        @ConfigEntry.Gui.RequiresRestart
        public boolean elytraMaceEnabled = true;

        @ConfigEntry.Category("general")
        @ConfigEntry.Gui.RequiresRestart
        public boolean rocketMaceEnabled = true;

        @ConfigEntry.Category("slots")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 8)
        public int attributeSwapSlot = 1;

        @ConfigEntry.Category("slots")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 8)
        public int spearSlot = 2;

        @ConfigEntry.Category("slots")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 8)
        public int pearlSlot = 3;

        @ConfigEntry.Category("slots")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 8)
        public int pearlCatchSecondSlot = 4;

        @ConfigEntry.Category("slots")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 8)
        public int maceSlot = 5;

        @ConfigEntry.Category("slots")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 8)
        public int rocketSlot = 6;

        @ConfigEntry.Category("delays")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int swapDelay = 1;

        @ConfigEntry.Category("delays")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int useDelay = 1;

        @ConfigEntry.Category("delays")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int attackDelay = 1;

        @ConfigEntry.Category("delays")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int pearlThrowDelay = 1;

        @ConfigEntry.Category("delays")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int secondItemDelay = 1;
    }
}