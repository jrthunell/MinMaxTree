package com.jeremythunell.minmaxtree.examples.simplenumbergame;

/**
 * Makes a simple binary tree with the given numbers
 */
public class SimpleNumberGame {

    private Number[] numbers;

    public SimpleNumberGame(Number... numbers){
        this.numbers = numbers;
    }

    public SimpleNumberGameState startGame() {
        return new SimpleNumberGameState(0, numbers);
    }
}
