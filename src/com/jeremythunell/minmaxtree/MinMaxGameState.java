package com.jeremythunell.minmaxtree;

import java.util.List;
import java.util.Map;

public interface MinMaxGameState <ActionType> {
    Map<MinMaxGameState<ActionType>, ActionType> getChildren();
    double evaluate();
    boolean isTerminal();
}
