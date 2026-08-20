package com.yellowbrossproductions.towerdefenseunits.client.model.animation;

public interface ICanBeAnimated {
    default float getAnimationSpeed(String input) {
        return 1f;
    }

    default float getAnimationSpeed() {
        return 1f;
    }

    default int maxSwingTime() {
        return 1;
    }
}
