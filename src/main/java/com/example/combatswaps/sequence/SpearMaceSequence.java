package com.example.combatswaps.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class SpearMaceSequence extends Sequence {
    private final int spearSlot;
    private final int maceSlot;
    private final int swapDelay;
    private final int useDelay;
    private final int attackDelay;

    public SpearMaceSequence(int originalSlot, int spearSlot, int maceSlot,
                             int swapDelay, int useDelay, int attackDelay) {
        super(originalSlot);
        this.spearSlot = spearSlot;
        this.maceSlot = maceSlot;
        this.swapDelay = swapDelay;
        this.useDelay = useDelay;
        this.attackDelay = attackDelay;
    }

    @Override
    public void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.world == null) return;

        switch (currentStep) {
            case 0: // Swap to spear slot
                setSlot(client, spearSlot);
                currentStep++;
                break;

            case 1: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 2: // Use spear (trident throw or use)
                useItem(client, Hand.MAIN_HAND);
                currentStep++;
                break;

            case 3: // Wait for use delay
                if (waitTicks(useDelay)) {
                    currentStep++;
                }
                break;

            case 4: // Swap to mace slot
                setSlot(client, maceSlot);
                currentStep++;
                break;

            case 5: // Wait for swap delay
                if (waitTicks(swapDelay)) {
                    currentStep++;
                }
                break;

            case 6: // Attack with mace
                if (client.targetedEntity != null) {
                    attack(client);
                    currentStep++;
                } else {
                    currentStep = 99;
                }
                break;

            case 7: // Wait for attack delay
                if (waitTicks(attackDelay)) {
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