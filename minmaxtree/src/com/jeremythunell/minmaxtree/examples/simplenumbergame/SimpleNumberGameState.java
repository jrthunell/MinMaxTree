package com.jeremythunell.minmaxtree.examples.simplenumbergame;

import com.jeremythunell.minmaxtree.minmaxtree.MinMaxGameState;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleNumberGameState implements MinMaxGameState<Boolean> {
    private int myIndex;
    private int leftChildIndex, rightChildIndex;
    private Number[] numbers;

    public SimpleNumberGameState(int index, Number[] numbers) {
        this.numbers = numbers;
        myIndex = index;
        leftChildIndex = index * 2 + 1;
        rightChildIndex = leftChildIndex + 1;
    }

    @Override
    public Map<MinMaxGameState<Boolean>, Boolean> getChildren() {
        if (isTerminal())
            return Collections.emptyMap();
        Map<MinMaxGameState<Boolean>, Boolean> children = new LinkedHashMap<>();
        children.put(new SimpleNumberGameState(leftChildIndex, numbers), true);
        if (rightChildIndex < numbers.length)
            children.put(new SimpleNumberGameState(rightChildIndex, numbers), false);
        return children;
    }

    @Override
    public double evaluate() {
        return numbers[myIndex].doubleValue();
    }

    @Override
    public boolean isTerminal() {
        return leftChildIndex >= numbers.length;
    }
}
