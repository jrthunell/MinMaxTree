package com.jeremythunell.minmaxtree.minmaxtree;

import java.util.Map;

public interface MinMaxGameState <ActionType> {
    Map<MinMaxGameState<ActionType>, ActionType> getChildren();
    double evaluate();
    boolean isTerminal();
}
