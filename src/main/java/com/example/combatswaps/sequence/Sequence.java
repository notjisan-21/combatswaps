package com.example.combatswaps.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public abstract class Sequence {
    protected final int originalSlot;
    protected int currentStep = 0;
    protected int stepTimer = 0;

    public Sequence(int originalSlot) {
        this.originalSlot = originalSlot;
    }

    public abstract void tick(MinecraftClient client);

    public abstract boolean isComplete();

    public void onComplete(MinecraftClient client) {
        // Restore original slot
        restoreSlot(client);
    }

    public void onCancel(MinecraftClient client) {
        // Restore original slot on cancel
        restoreSlot(client);
    }

    protected void restoreSlot(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player != null && player.getInventory().selectedSlot != originalSlot) {
            player.getInventory().selectedSlot = originalSlot;
            // Sync with server
            player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket(
                net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND,
                player.getBlockPos(),
                player.getHorizontalFacing()
            ));
        }
    }

    protected void setSlot(MinecraftClient client, int slot) {
        ClientPlayerEntity player = client.player;
        if (player != null && player.getInventory().selectedSlot != slot) {
            player.getInventory().selectedSlot = slot;
            // The slot change is automatically synced via PlayerInteractItemC2SPacket when using items
        }
    }

    protected void attack(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player != null) {
            client.interactionManager.attackEntity(player, client.targetedEntity);
        }
    }

    protected void useItem(MinecraftClient client, net.minecraft.util.Hand hand) {
        ClientPlayerEntity player = client.player;
        if (player != null) {
            client.interactionManager.interactItem(player, hand);
        }
    }

    protected boolean waitTicks(int ticks) {
        stepTimer++;
        if (stepTimer >= ticks) {
            stepTimer = 0;
            currentStep++;
            return true;
        }
        return false;
    }
}