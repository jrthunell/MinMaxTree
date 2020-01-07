package com.jeremythunell.minmaxtree.minmaxtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MaxNode<ActionType> extends MinMaxTreeNode<ActionType> {
    /*package-private*/ MaxNode(MinMaxGameState gameState, MinNode parent) {
        super(gameState, parent);
    }

    @Override
    protected void onChildUpdated() {
        double maxValue = Double.MIN_VALUE;
        for (Map.Entry<MinMaxTreeNode<ActionType>, ActionType> child : this.actions.entrySet()) {
            MinMaxTreeNode<ActionType> childNode = child.getKey();
            ActionType action = child.getValue();
            if (childNode.getValue() > maxValue) {
                maxValue = childNode.getValue();
                this.bestAction = action;
                this.bestChild = childNode;
            }
        }
        if (parent != null)
            parent.onChildUpdated();
    }

    @Override
    protected List<MinMaxTreeNode<ActionType>> exploreNode(Map<MinMaxGameState<ActionType>, MinMaxGameState<ActionType>> existingNodes) {
        if (this.isTerminal) {
            this.isExplored = true;
            return Collections.emptyList();
        }
        if (this.isExplored) {
            return children;
        }
        List<MinMaxTreeNode<ActionType>> children = new ArrayList<>();
        double maxValue = -Double.MAX_VALUE;
        for (Map.Entry<MinMaxGameState<ActionType>, ActionType> child : this.gameState.getChildren().entrySet()) {
            MinMaxGameState<ActionType> childGameState = child.getKey();
            if(existingNodes.containsKey(childGameState)){
                // if we've already seen a node, then just use the existing one to avoid unnecessary computation and loops
                childGameState = existingNodes.get(childGameState);
            } else {
                existingNodes.put(childGameState, childGameState);
            }
            ActionType action = child.getValue();
            MinNode<ActionType> childNode = new MinNode<>(childGameState, this);
            children.add(childNode);
            actions.put(childNode, action);
            if (childNode.getValue() > maxValue) {
                maxValue = childNode.getValue();
                this.bestAction = action;
                this.bestChild = childNode;
            }
        }
        this.children = children;
        this.isExplored = true;
        if (parent != null)
            parent.onChildUpdated();
        return children;
    }

    @Override
    public ActionType getAction() {
        return bestAction;
    }
}
