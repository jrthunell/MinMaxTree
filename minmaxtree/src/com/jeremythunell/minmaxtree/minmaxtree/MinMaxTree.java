package com.jeremythunell.minmaxtree.minmaxtree;

import java.util.*;
import java.util.stream.Collectors;

public class MinMaxTree<ActionType> {
    private MinMaxTreeNode<ActionType> root;
    Map<MinMaxGameState<ActionType>,MinMaxGameState<ActionType>> existingNodes = new HashMap<>();

    private MinMaxTree(MinMaxTreeNode<ActionType> root) {
        this.root = root;
        root.exploreNode(existingNodes);
    }

    public static <T> MinMaxTree<T> minMaxTree(MinMaxGameState<T> root) {
        return new MinMaxTree<>(new MaxNode<>(root, null)); // minmax trees have a max node as the root (to minimize the maximal loss)
    }

    public static <T> MinMaxTree<T> maxMinTree(MinMaxGameState<T> root) {
        return new MinMaxTree<>(new MinNode<>(root, null)); // maxmin trees have a min node as the root (to maximize the minimal gain)
    }

    public void exploreToDepth(int depth){
        Queue<MinMaxTreeNode<ActionType>> toExplore = new LinkedList<>();
        toExplore.add(root);
        exploreToDepth(depth - 1, toExplore);
    }

    private void exploreToDepth(int depth, Queue<MinMaxTreeNode<ActionType>> nodesToExplore){
        if(depth == 0){
            for(MinMaxTreeNode<ActionType> node : nodesToExplore)
                node.exploreNode(existingNodes);
        } else {
            Queue<MinMaxTreeNode<ActionType>> nextDepth = new LinkedList<>();
            for(MinMaxTreeNode<ActionType> node : nodesToExplore) {
                nextDepth.addAll(node.exploreNode(existingNodes));
            }
            exploreToDepth(depth - 1, nextDepth);
        }
    }

    public void exploreNNodes(int n){
        Queue<MinMaxTreeNode<ActionType>> toExplore = new LinkedList<>();
        toExplore.add(root);
        int numExplored = 0;
        while(numExplored < n && !toExplore.isEmpty()){
            MinMaxTreeNode<ActionType> node = toExplore.poll();
            if(!node.isExplored)
                numExplored++;
            toExplore.addAll(node.exploreNode(existingNodes));
        }
    }

    public void exploreFullTree(){
        Queue<MinMaxTreeNode<ActionType>> toExplore = new LinkedList<>();
        toExplore.add(root);
        while(!toExplore.isEmpty()){
            MinMaxTreeNode<ActionType> node = toExplore.poll();
            toExplore.addAll(node.exploreNode(existingNodes));
        }
    }

    public ActionType getAction(){
        return root.getAction();
    }

    /**
     * Changes the root to the GameState following the given action
     */
    public void makeAction(ActionType action){
        for(Map.Entry<MinMaxTreeNode<ActionType>, ActionType> child : root.actions.entrySet()){
            if(child.getValue().equals(action)){
                this.root = child.getKey();
                //System.out.println("did action: " + root + ": " + child.getValue() + " with value: " + root.getValue());
                root.exploreNode(existingNodes);
                //System.out.println("children: " + root.actions.entrySet().stream().map(x -> "\n" + x.getKey() + ": " + x.getValue()  + ": " + x.getKey().getValue()).collect(Collectors.toList()));
                return;
            }
        }
        throw new IllegalArgumentException("Could not find a child node associated with action: " + action);
    }
}
