package com.jeremythunell.examples.simplenumbergame;

import com.jeremythunell.minmaxtree.MinMaxGameState;

import java.util.Map;

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
