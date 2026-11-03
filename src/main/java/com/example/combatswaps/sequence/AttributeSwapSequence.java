package com.example.combatswaps.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class AttributeSwapSequence extends Sequence {
    private final int targetSlot;
    private final int swapDelay;
    private final int attackDelay;

    public AttributeSwapSequence(int originalSlot, int targetSlot, int swapDelay, int attackDelay) {
        super(originalSlot);
        this.targetSlot = targetSlot;
        this.swapDelay = swapDelay;
        this.attackDelay = attackDelay;
    }

    @Override
    public void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.world == null) return;

        switch (currentStep) {
            case 0: // Swap to target slot
                setSlot(client, targetSlot);
                currentStep++;
                break;

            case 1: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 2: // Attack entity under crosshair
                if (client.targetedEntity != null) {
                    attack(client);
                    currentStep++;
                } else {
                    // No entity to attack, cancel
                    currentStep = 99; // Will complete and restore
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