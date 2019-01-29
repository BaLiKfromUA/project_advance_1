package advanced.balik.application.model;

import advanced.balik.application.graph.Turn;
import advanced.balik.application.view.Action;

import java.util.ArrayList;

public class PersistentLeftistHeap {
    private LeftHeapNode root;
    private PersistentLeftistHeap previous;
    private ArrayList<Turn> mergeLog;
    private String currTurnLog;

    public String getCurrTurnLog() {
        return currTurnLog;
    }

    public void setCurrTurnLog(String currTurnLog) {
        this.currTurnLog = currTurnLog;
    }

    private PersistentLeftistHeap(LeftHeapNode node, PersistentLeftistHeap prev) {
        root = node;
        previous = prev;
    }

    public PersistentLeftistHeap getPrevious() {
        return previous;
    }

    public PersistentLeftistHeap() {
        root = null;
        previous = null;
    }

    public PersistentLeftistHeap(LeftHeapNode node) {
        root = node;
        previous = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public PersistentLeftistHeap clear() {
        return new PersistentLeftistHeap(null, this);
    }

    private LeftHeapNode logRoot = null;
    private int level = 0;

    public PersistentLeftistHeap insert(int x) {
        mergeLog = new ArrayList<>();
        LeftHeapNode newRoot;
        if (root != null) {
            newRoot = root.copy();
        } else {
            newRoot = null;
        }

        if (root != null) {
            logRoot = root.copy();
        }
        mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot), Action.INSERT_BEGIN.getAction(),
                1001));//because 999 is maximum
        level = 0;
        PersistentLeftistHeap persistentLeftistHeap =
                new PersistentLeftistHeap(merge(newRoot, new LeftHeapNode(x), null), this);

        persistentLeftistHeap.setMergeLog(mergeLog);
        return persistentLeftistHeap;
    }

    private LeftHeapNode merge(LeftHeapNode leftTree, LeftHeapNode rightTree, LeftHeapNode parent) {
        level++;
        if (leftTree == null) {
            if (rightTree != null) {
                if (logRoot == null) {
                    mergeLog.add(new Turn(new PersistentLeftistHeap(rightTree.copy()),
                            Action.LEFT_NULL.getAction(), rightTree.element));
                } else {
                    if (parent != null) {
                        parent.right = rightTree;
                    }
                    mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()),
                            Action.LEFT_NULL.getAction(), rightTree.element));
                }
            }
            return rightTree;
        }
        if (rightTree == null) {
            if (logRoot == null) {
                mergeLog.add(new Turn(new PersistentLeftistHeap(leftTree.copy()), Action.RIGHT_NULL.getAction(),
                        leftTree.element));
            } else {
                if (parent != null) {
                    parent.right = leftTree;
                }
                mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.RIGHT_NULL.getAction(),
                        leftTree.element));
            }
            return leftTree;
        }

        if (leftTree.element > rightTree.element) {

            if (logRoot != null) {
                mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.MIN_ELEMENT_RIGHT.getAction(),
                        rightTree.element));
            }
            LeftHeapNode temp = leftTree;
            leftTree = rightTree;
            rightTree = temp;


        }

        if (level == 1) {
            logRoot = leftTree;
        }

        mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.MIN_ELEMENT_LEFT.getAction(),
                leftTree.element));

        leftTree.right = merge(leftTree.right, rightTree, leftTree);

        if (logRoot != null) {
            mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.RECONNECTING_TREE.getAction(),
                    leftTree.element));
        }

        if (leftTree.left == null) {
            if (logRoot != null) {
                mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()),
                        String.format(Action.LEFT_CHILD_IS_NULL.getAction(), leftTree.element),
                        leftTree.element));
            }
            leftTree.left = leftTree.right;
            leftTree.right = null;
            if (logRoot != null) {
                mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.RECONNECTING_TREE.getAction(),
                        leftTree.left.element));
            }

        } else {
            if (leftTree.left.sValue < leftTree.right.sValue) {
                if (logRoot != null) {
                    mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.RIGHT_SUBTREE_LARGER.getAction(),
                            leftTree.right.element));
                }
                LeftHeapNode temp = leftTree.left;
                leftTree.left = leftTree.right;
                leftTree.right = temp;
            }
            if (logRoot != null) {
                mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot.copy()), Action.LEFT_SUBTREE_LARGER.getAction(),
                        leftTree.left.element));
            }
            leftTree.sValue = leftTree.right.sValue + 1;
        }

        return leftTree;
    }

    public int getMin() {
        if (isEmpty())
            return -1;
        return root.element;
    }

    public PersistentLeftistHeap extractMin() {
        mergeLog = new ArrayList<>();

        if (root == null) {
            return new PersistentLeftistHeap(null, this);
        }
        LeftHeapNode newLeft;
        if (root.left != null) {
            newLeft = root.left.copy();
        } else {
            newLeft = null;
        }

        LeftHeapNode newRight;
        if (root.right != null) {
            newRight = root.right.copy();
        } else {
            newRight = null;
        }

        if (root != null) {
            logRoot = root.copy();
        }
        mergeLog.add(new Turn(new PersistentLeftistHeap(logRoot), Action.EXTRACT_BEGIN.getAction(),
                this.getMin()));
        level = 0;

        PersistentLeftistHeap persistentLeftistHeap = new PersistentLeftistHeap(merge(newLeft, newRight, null), this);
        persistentLeftistHeap.setMergeLog(mergeLog);

        return persistentLeftistHeap;
    }

    public LeftHeapNode getRoot() {
        return root;
    }

    public int height() {
        int level;
        if (isEmpty()) {
            level = 0;
        } else {
            level = getHeight(1, root);
        }
        return level;
    }

    /**
     * Рекурсивный метод получения высоты дерева.
     *
     * @param level текущий уровень вершины на дереве, считая от корня.
     *              Инициализируется с изначальным значением = 1.
     * @return высота дерева
     */
    private int getHeight(int level, LeftHeapNode root) {
        if (root == null) {
            return level;
        }
        PersistentLeftistHeap left = new PersistentLeftistHeap(root.getLeft(), this);
        PersistentLeftistHeap right = new PersistentLeftistHeap(root.getRight(), this);

        int nextLevel = level + 1;

        if (left.isEmpty() && right.isEmpty()) {
            return level;
        }

        int rightHeight = right.getHeight(nextLevel, right.getRoot());
        int leftHeight = left.getHeight(nextLevel, left.getRoot());

        if (left.isEmpty()) {
            return rightHeight;
        }

        if (right.isEmpty()) {
            return leftHeight;
        }

        if (leftHeight > rightHeight) {
            return leftHeight;
        } else {
            return rightHeight;
        }
    }

    public ArrayList<Turn> getMergeLog() {
        return mergeLog;
    }

    public void setMergeLog(ArrayList<Turn> mergeLog) {
        this.mergeLog = mergeLog;
    }
}
