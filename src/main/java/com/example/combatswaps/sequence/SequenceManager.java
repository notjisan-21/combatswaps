package com.example.combatswaps.sequence;

import com.example.combatswaps.CombatSwapsClient;
import com.example.combatswaps.config.CombatSwapsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;

public class SequenceManager {
    private Sequence currentSequence = null;

    public void tick(MinecraftClient client) {
        // Check if we should cancel current sequence
        if (currentSequence != null) {
            if (shouldCancelSequence(client)) {
                cancelSequence(client);
                return;
            }

            // Tick the current sequence
            currentSequence.tick(client);

            // Check if sequence is complete
            if (currentSequence.isComplete()) {
                currentSequence.onComplete(client);
                currentSequence = null;
            }
        }
    }

    private boolean shouldCancelSequence(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.world == null) {
            return true;
        }

        // Cancel if player died
        if (!player.isAlive()) {
            return true;
        }

        // Cancel if a screen is open (except inventory/hud)
        if (client.currentScreen != null && !(client.currentScreen instanceof net.minecraft.client.gui.screen.ingame.HandledScreen<?>)) {
            return true;
        }

        // Cancel if world unloaded (handled by player == null check above)

        return false;
    }

    private void cancelSequence(MinecraftClient client) {
        if (currentSequence != null) {
            currentSequence.onCancel(client);
            currentSequence = null;
        }
    }

    public boolean isRunningSequence() {
        return currentSequence != null;
    }

    public void startAttributeSwap(MinecraftClient client) {
        if (isRunningSequence()) return;
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;
        if (!config.attributeSwapEnabled) return;

        int originalSlot = client.player.getInventory().selectedSlot;
        int targetSlot = config.attributeSwapSlot;

        if (originalSlot == targetSlot) return; // Already in target slot

        // Check if target slot has a valid weapon
        ItemStack targetStack = client.player.getInventory().getStack(targetSlot);
        if (targetStack.isEmpty() || !isWeapon(targetStack)) return;

        currentSequence = new AttributeSwapSequence(originalSlot, targetSlot, config.swapDelay, config.attackDelay);
    }

    public void startSpearSwap(MinecraftClient client) {
        if (isRunningSequence()) return;
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;
        if (!config.spearSwapEnabled) return;

        int originalSlot = client.player.getInventory().selectedSlot;
        int targetSlot = config.spearSlot;

        if (originalSlot == targetSlot) return;

        ItemStack targetStack = client.player.getInventory().getStack(targetSlot);
        if (targetStack.isEmpty() || !isSpear(targetStack)) return;

        currentSequence = new SpearSwapSequence(originalSlot, targetSlot, config.swapDelay, config.useDelay);
    }

    public void startPearlCatch(MinecraftClient client) {
        if (isRunningSequence()) return;
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;
        if (!config.pearlCatchEnabled) return;

        int originalSlot = client.player.getInventory().selectedSlot;
        int pearlSlot = config.pearlSlot;
        int secondSlot = config.pearlCatchSecondSlot;

        if (originalSlot == pearlSlot || originalSlot == secondSlot) return;

        ItemStack pearlStack = client.player.getInventory().getStack(pearlSlot);
        ItemStack secondStack = client.player.getInventory().getStack(secondSlot);

        if (pearlStack.isEmpty() || pearlStack.getItem() != Items.ENDER_PEARL) return;
        if (secondStack.isEmpty() || (!isWindCharge(secondStack) && !isMace(secondStack))) return;

        currentSequence = new PearlCatchSequence(originalSlot, pearlSlot, secondSlot,
            config.swapDelay, config.pearlThrowDelay, config.secondItemDelay);
    }

    public void startSpearMace(MinecraftClient client) {
        if (isRunningSequence()) return;
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;
        if (!config.spearMaceEnabled) return;

        int originalSlot = client.player.getInventory().selectedSlot;
        int spearSlot = config.spearSlot;
        int maceSlot = config.maceSlot;

        if (originalSlot == spearSlot || originalSlot == maceSlot) return;

        ItemStack spearStack = client.player.getInventory().getStack(spearSlot);
        ItemStack maceStack = client.player.getInventory().getStack(maceSlot);

        if (spearStack.isEmpty() || !isSpear(spearStack)) return;
        if (maceStack.isEmpty() || !isMace(maceStack)) return;

        currentSequence = new SpearMaceSequence(originalSlot, spearSlot, maceSlot,
            config.swapDelay, config.useDelay, config.attackDelay);
    }

    public void startElytraMace(MinecraftClient client) {
        if (isRunningSequence()) return;
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;
        if (!config.elytraMaceEnabled) return;

        ClientPlayerEntity player = client.player;
        if (!player.isFallFlying()) return; // Only works while gliding

        int originalSlot = player.getInventory().selectedSlot;
        int maceSlot = config.maceSlot;

        if (originalSlot == maceSlot) return;

        ItemStack maceStack = player.getInventory().getStack(maceSlot);
        if (maceStack.isEmpty() || !isMace(maceStack)) return;

        currentSequence = new ElytraMaceSequence(originalSlot, maceSlot, config.swapDelay, config.attackDelay);
    }

    public void startRocketMace(MinecraftClient client) {
        if (isRunningSequence()) return;
        CombatSwapsConfig config = CombatSwapsClient.CONFIG;
        if (!config.rocketMaceEnabled) return;

        ClientPlayerEntity player = client.player;
        if (!player.isFallFlying()) return; // Only works while gliding

        int originalSlot = player.getInventory().selectedSlot;
        int rocketSlot = config.rocketSlot;
        int maceSlot = config.maceSlot;

        if (originalSlot == rocketSlot || originalSlot == maceSlot) return;

        ItemStack rocketStack = player.getInventory().getStack(rocketSlot);
        ItemStack maceStack = player.getInventory().getStack(maceSlot);

        if (rocketStack.isEmpty() || rocketStack.getItem() != Items.FIREWORK_ROCKET) return;
        if (maceStack.isEmpty() || !isMace(maceStack)) return;

        currentSequence = new RocketMaceSequence(originalSlot, rocketSlot, maceSlot,
            config.swapDelay, config.useDelay, config.attackDelay);
    }

    // Item validation helpers
    private boolean isWeapon(ItemStack stack) {
        return stack.getItem() instanceof net.minecraft.item.SwordItem
            || stack.getItem() instanceof net.minecraft.item.AxeItem
            || stack.getItem() instanceof net.minecraft.item.MaceItem
            || stack.getItem() == Items.TRIDENT;
    }

    private boolean isSpear(ItemStack stack) {
        return stack.getItem() == Items.TRIDENT;
    }

    private boolean isMace(ItemStack stack) {
        return stack.getItem() instanceof net.minecraft.item.MaceItem;
    }

    private boolean isWindCharge(ItemStack stack) {
        return stack.getItem() == Items.WIND_CHARGE;
    }
}