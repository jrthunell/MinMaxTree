package com.jeremythunell.minmaxtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MinNode<ActionType> extends MinMaxTreeNode<ActionType> {
    /*package-private*/ MinNode(MinMaxGameState gameState, MaxNode parent) {
        super(gameState, parent);
    }

    @Override
    protected void onChildUpdated() {
        double minValue = Double.MAX_VALUE;
        for (Map.Entry<MinMaxTreeNode<ActionType>, ActionType> child : this.actions.entrySet()) {
            MinMaxTreeNode<ActionType> childNode = child.getKey();
            ActionType action = child.getValue();
            if (childNode.getValue() < minValue) {
                minValue = childNode.getValue();
                this.bestAction = action;
                this.bestChild = childNode;
            }
        }
        if (parent != null)
            parent.onChildUpdated();
    }

    @Override
    protected List<MinMaxTreeNode<ActionType>> exploreNode() {
        if (this.isTerminal) {
            this.isExplored = true;
            return Collections.emptyList();
        }
        if (this.isExplored) {
            return children;
        }
        List<MinMaxTreeNode<ActionType>> children = new ArrayList<>();
        double minValue = Double.MAX_VALUE;
        for (Map.Entry<MinMaxGameState<ActionType>, ActionType> child : this.gameState.getChildren().entrySet()) {
            MinMaxGameState<ActionType> childGameState = child.getKey();
            ActionType action = child.getValue();
            MaxNode<ActionType> childNode = new MaxNode<>(childGameState, this);
            children.add(childNode);
            actions.put(childNode, action);
            if (childNode.getValue() < minValue) {
                minValue = childNode.getValue();
                this.bestAction = action;
                this.bestChild = childNode;
            }
        }
        this.children = children;
        this.isExplored = true;
        parent.onChildUpdated();
        return children;
    }

    @Override
    public ActionType getAction() {
        return bestAction;
    }
}
