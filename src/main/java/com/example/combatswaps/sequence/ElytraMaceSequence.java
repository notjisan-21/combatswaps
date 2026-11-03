package com.example.combatswaps.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class ElytraMaceSequence extends Sequence {
    private final int maceSlot;
    private final int swapDelay;
    private final int attackDelay;

    public ElytraMaceSequence(int originalSlot, int maceSlot, int swapDelay, int attackDelay) {
        super(originalSlot);
        this.maceSlot = maceSlot;
        this.swapDelay = swapDelay;
        this.attackDelay = attackDelay;
    }

    @Override
    public void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.world == null) return;

        // Check if still gliding
        if (!player.isFallFlying()) {
            currentStep = 99; // Cancel and restore
            return;
        }

        switch (currentStep) {
            case 0: // Swap to mace slot
                setSlot(client, maceSlot);
                currentStep++;
                break;

            case 1: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 2: // Attack entity under crosshair with mace
                if (client.targetedEntity != null) {
                    attack(client);
                    currentStep++;
                } else {
                    currentStep = 99;
                }
                break;

            case 3: // Wait for attack delay
                if (waitTicks(attackDelay)) {
                    currentStep++;
                }
                break;

            case 4: // Swap back to original slot
                setSlot(client, originalSlot);
                currentStep++;
                break;
        }
    }

    @Override
    public boolean isComplete() {
        return currentStep >= 5;
    }
}