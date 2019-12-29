package com.jeremythunell.minmaxtree;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MinMaxTreeNode <ActionType>{
    protected MinMaxTreeNode<ActionType> bestChild = null;
    protected ActionType bestAction;
    protected boolean isExplored = false;
    protected List<MinMaxTreeNode<ActionType>> children;
    protected Map<MinMaxTreeNode<ActionType>, ActionType> actions = new LinkedHashMap<>();
    protected MinMaxGameState<ActionType> gameState;
    @Nullable
    protected MinMaxTreeNode<ActionType> parent;
    protected boolean isTerminal;

    public MinMaxTreeNode(MinMaxGameState<ActionType> gameState, MinMaxTreeNode<ActionType> parent){
        this.gameState = gameState;
        this.parent = parent;
        this.isTerminal = gameState.isTerminal();
    }

    public double getValue(){
        if(isExplored && !isTerminal && children.size() > 0)
            return bestChild.getValue();
        else
            return this.gameState.evaluate();
    }

    /**
     * When a child node is explored, it reports its new value using this method
     */
    protected abstract void onChildUpdated();

    /**
     * Gets the children and updates the value of this node appropriately
     */
    protected abstract List<MinMaxTreeNode<ActionType>> exploreNode();

    /**
     * Gets the action associated with the min/max node's best value
     * @return
     */
    public abstract ActionType getAction();
}
