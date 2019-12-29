package com.jeremythunell.minmaxtree;

import com.jeremythunell.examples.simplenumbergame.SimpleNumberGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxTreeTest {

    @Test
    void testMinMaxTreeFactory() {
        // with a MinMax Tree, the root should be a max node, so it should want to go down the right branch, which is the "false" branch
        MinMaxTree<Boolean> tree = MinMaxTree.minMaxTree(makeTestGame().startGame());
        assertFalse(tree.getAction());
    }

    @Test
    void maxMinTree() {
        // with a MaxMin Tree, the root should be a min node, so it should want to go down the left branch, which is the "true" branch
        MinMaxTree<Boolean> tree = MinMaxTree.maxMinTree(makeTestGame().startGame());
        assertTrue(tree.getAction());
    }

    @Test
    void exploreToDepth() {
        MinMaxTree<Boolean> tree = MinMaxTree.minMaxTree(makeTestGame().startGame());
        // explore to depth 1 (just the root, shouldn't do anything)
        tree.exploreToDepth(1);
        assertFalse(tree.getAction());

        // explore to depth 2, which is a min-node layer. The left branch will be 7 and the right branch will be 3.
        // The root (max-node) will pick the 7, which is the left branch
        tree.exploreToDepth(2);
        assertTrue(tree.getAction());

        // explore to depth 3, which is a max-node layer and fully explores the tree. The left branch will be 79 and 68,
        // the right branch will be 76 and 89. The second depth min-node layer will pick 68 on the left and 76 on the right.
        // The max-node root will then pick 76, on the right
        tree.exploreToDepth(3);
        assertFalse(tree.getAction());
    }

    @Test
    void exploreNNodes() {
        MinMaxTree<Boolean> tree = MinMaxTree.minMaxTree(makeTestGame().startGame());
        // the root is explored by default and should want to go down the right branch
        assertFalse(tree.getAction());

        // explore 3 more nodes, the 86 min-node, the 92 max-node and the 7 max-node. 86 will update to 3, 92 will update to
        // 79 and 7 will update to 68. So the min-node layer will choose 68 on the left and 3 on the right. The root will pick 68 on the left.
        tree.exploreNNodes(3);
        assertTrue(tree.getAction());

        // explore more nodes than remain in the tree
        tree.exploreNNodes(10000);
        assertFalse(tree.getAction());
    }

    @Test
    void exploreFullTree(){
        MinMaxTree<Boolean> tree = MinMaxTree.minMaxTree(makeTestGame().startGame());
        tree.exploreNNodes(2);
        assertTrue(tree.getAction());
        tree.exploreFullTree();
        assertFalse(tree.getAction());
    }

    @Test
    void makeAction() {
    }

    private SimpleNumberGame makeTestGame() {
        /*
         * MAX                    76
         *               /                 \
         * MIN          65                  86
         *           /       \          /       \
         * MAX      92       7         3        50
         *        /   \    /   \     /   \    /   \
         * TERM  79   8   10   68   76   3   53   89
         */
        return new SimpleNumberGame(76, 65, 86, 92, 7, 3, 50, 79, 8, 10, 68, 76, 3, 53, 89);
    }
}