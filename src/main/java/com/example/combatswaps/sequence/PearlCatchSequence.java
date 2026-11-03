package com.example.combatswaps.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class PearlCatchSequence extends Sequence {
    private final int pearlSlot;
    private final int secondSlot;
    private final int swapDelay;
    private final int pearlThrowDelay;
    private final int secondItemDelay;

    public PearlCatchSequence(int originalSlot, int pearlSlot, int secondSlot,
                              int swapDelay, int pearlThrowDelay, int secondItemDelay) {
        super(originalSlot);
        this.pearlSlot = pearlSlot;
        this.secondSlot = secondSlot;
        this.swapDelay = swapDelay;
        this.pearlThrowDelay = pearlThrowDelay;
        this.secondItemDelay = secondItemDelay;
    }

    @Override
    public void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.world == null) return;

        switch (currentStep) {
            case 0: // Swap to ender pearl slot
                setSlot(client, pearlSlot);
                currentStep++;
                break;

            case 1: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 2: // Throw ender pearl
                useItem(client, Hand.MAIN_HAND);
                currentStep++;
                break;

            case 3: // Wait for pearl throw delay
                if (waitTicks(pearlThrowDelay)) {
                    currentStep++;
                }
                break;

            case 4: // Swap to second slot (wind charge or mace)
                setSlot(client, secondSlot);
                currentStep++;
                break;

            case 5: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 6: // Use second item (wind charge or mace)
                useItem(client, Hand.MAIN_HAND);
                currentStep++;
                break;

            case 7: // Wait for second item delay
                if (waitTicks(secondItemDelay)) {
                    currentStep++;
                }
                break;

            case 8: // Swap back to original slot
                setSlot(client, originalSlot);
                currentStep++;
                break;
        }
    }

    @Override
    public boolean isComplete() {
        return currentStep >= 9;
    }
}