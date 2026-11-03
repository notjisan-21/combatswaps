package com.example.combatswaps.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class SpearSwapSequence extends Sequence {
    private final int targetSlot;
    private final int swapDelay;
    private final int useDelay;

    public SpearSwapSequence(int originalSlot, int targetSlot, int swapDelay, int useDelay) {
        super(originalSlot);
        this.targetSlot = targetSlot;
        this.swapDelay = swapDelay;
        this.useDelay = useDelay;
    }

    @Override
    public void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.world == null) return;

        switch (currentStep) {
            case 0: // Swap to spear slot
                setSlot(client, targetSlot);
                currentStep++;
                break;

            case 1: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 2: // Use/attack with spear
            case 3: // Use with offhand if needed
                useItem(client, Hand.MAIN_HAND);
                currentStep++;
                break;

            case 4: // Wait for use delay
                if (waitTicks(useDelay)) {
                    currentStep++;
                }
                break;

            case 5: // Swap back to original slot
                setSlot(client, originalSlot);
                currentStep++;
                break;
        }
    }

    @Override
    public boolean isComplete() {
        return currentStep >= 6;
    }
}