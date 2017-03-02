
/**
 * Created by chang on 2/24/17.
 * an AvlTree-liked class which support insert, deleted, search certain node and In-Order Traversal
 */
package avl;

import java.util.LinkedList;

public class AvlTree {

    private Node root;

    private int size;

    private Object deleted;     // help return the obj which needed to be delete

    public AvlTree() {
        this.root = null;
        this.size = 0;
        this.deleted = null;
    }

    public int getSize() {
        return this.size;
    }

    // search n-th node
    public Node search(int n) {
        if ((n > this.size - 1) || (n < 0)) {
            throw new IndexOutOfBoundsException("index is out of range");
        } else {
            Node point = this.root;

            // index of current point
            int i = point.numberOfNodesOnLeft;

            while (i != n) {

                //since index is larger than n, should go left
                if (i > n) {

                    //go left
                    int tmp = point.numberOfNodesOnLeft;
                    point = point.left;
                    i = i - tmp + point.numberOfNodesOnLeft;
                } else if (i < n) {

                    //go right
                    point = point.right;
                    i = i + point.numberOfNodesOnLeft + 1;
                }
            }
            return point;
        }
    }

    // insertion. nIndex denote newNode's Index
    private Node recursiveInsert(Node newNode, Node parent, int nIndex, int leftBase) {

        // index of parent
        int pIndex = leftBase + parent.numberOfNodesOnLeft;

        if (nIndex <= pIndex) {
            parent.numberOfNodesOnLeft++;

            //attach new node as leaf
            if (parent.left == null) {
                parent.left = newNode;
            } else {

                // branch left
                parent.left = recursiveInsert(newNode, parent.left, nIndex, leftBase);
                parent = rebalance(parent);
            }
        } else if (nIndex > pIndex) {
            leftBase = pIndex + 1;

            // attach new node as leaf
            if (parent.right == null) {
                parent.right = newNode;
            } else {

                // branch right
                parent.right = recursiveInsert(newNode, parent.right, nIndex, leftBase);
                parent = rebalance(parent);
            }
        }

        updateHeight(parent);

        // return new root of this subtree
        return parent;

    }

    public void insert(int i, Object obj) {

        // Since this AvlTree is modified for List
        if (i > this.size || i < 0) {
            throw new IndexOutOfBoundsException("index is out of range");
        } else {
            this.size++;
        }

        Node newNode = new Node(obj);

        // index of its parent + 1
        int leftBase = 0;
        if (this.root == null) {
            this.root = newNode;
        } else {
            this.root = recursiveInsert(newNode, this.root, i, leftBase);

        }
    }

    // recursive delete
    private Node remove(Node parent, int dIndex, int leftBase) {

        int pIndex = leftBase + parent.numberOfNodesOnLeft;

        if (dIndex < pIndex) {        // the node is in left side
            parent.numberOfNodesOnLeft--;

            parent.left = remove(parent.left, dIndex, leftBase);
            parent = rebalance(parent);
        } else if (dIndex > pIndex) {    // the node is in right side
            leftBase = pIndex + 1;

            parent.right = remove(parent.right, dIndex, leftBase);
            parent = rebalance(parent);
        } else {

            //parent is the node would be removed
            if ((parent.left != null) && (parent.right != null)) {
                if (getHeight(parent.left) > getHeight(parent.right)) {

                    // switch the object of the most right descendant of left child and the object of the node
                    // Thus reduce consumption of switching
                    Node lMax = getMax(parent.left);
                    parent.numberOfNodesOnLeft--;

                    Object tmp = parent.obj;
                    parent.obj = lMax.obj;
                    lMax.obj = tmp;

                    int lMaxIndex = dIndex - 1;

                    parent.left = remove(parent.left, lMaxIndex, leftBase);
                } else {

                    // switch the object of the most left descendant of right child and the object of the node
                    // Thus reduce consumption of switching
                    Node rMin = getMin(parent.right);
                    Object tmp = parent.obj;
                    parent.obj = rMin.obj;
                    rMin.obj = tmp;

                    int rMinIndex = dIndex + 1;
                    leftBase = pIndex + 1;

                    parent.right = remove(parent.right, rMinIndex, leftBase);
                }

            } else {
                this.deleted = parent.obj;
                if (parent.left != null) {
                    parent = parent.left;
                } else {
                    parent = parent.right;
                }
            }
        }

        // calculate the height again
        if (parent != null) {
            updateHeight(parent);
        }
        return parent;
    }

    public Object delete(int i) {
        this.deleted = null;

        // Since this AvlTree is modified for List
        if ((i > this.size - 1) || (i < 0)) {
            throw new IndexOutOfBoundsException("index is out of range");
        } else {
            this.size--;
            int leftBase = 0;
            this.root = remove(this.root, i, leftBase);
        }
        return this.deleted;
    }


    /*
    AvlTree helper functions
    */

    //height of subtree rooted at x
    public int getHeight(Node x) {

        // -1 + 1 = 0, which would be helpful when calculating the height
        return (x == null ? -1 : x.height);
    }

    private Node rotateWithLeftChild(Node P) {

        // left child of P
        Node L = P.left;
        P.numberOfNodesOnLeft = P.numberOfNodesOnLeft - L.numberOfNodesOnLeft - 1;  // 1 for L

        P.left = L.right;
        L.right = P;

        P.height = Math.max(getHeight(P.left), getHeight(P.right)) + 1;
        L.height = Math.max(getHeight(L.left), getHeight(L.right)) + 1;
        return L;
    }

    private Node rotateWithRightChild(Node P) {

        // right child of P
        Node R = P.right;
        R.numberOfNodesOnLeft = R.numberOfNodesOnLeft + P.numberOfNodesOnLeft + 1;  // 1 for P

        P.right = R.left;
        R.left = P;

        P.height = Math.max(getHeight(P.left), getHeight(P.right)) + 1;
        R.height = Math.max(getHeight(R.left), getHeight(R.right)) + 1;
        return R;
    }

    //parent rotate with right child then grand rotate with left child. G for grand
    private Node doubleRotateWithLeftParent(Node G) {
        G.left = rotateWithRightChild(G.left);
        return rotateWithLeftChild(G);
    }

    //parent rotate with left child then grand rotate with right child. G for grand
    private Node doubleRotateWithRightParent(Node G) {
        G.right = rotateWithLeftChild(G.right);
        return rotateWithRightChild(G);
    }

    // return the most right descendant of the node
    private Node getMax(Node node) {
        if (node == null) {
            return null;
        } else {
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }
    }

    // return the most left descendant of the node
    private Node getMin(Node node) {
        if (node == null) {
            return null;
        } else {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
    }

    //In-Order Traversal return a LinkedList. This is for test
    private void inOrder(Node node, LinkedList list) {

        if (node != null) {
            inOrder(node.left, list);
            list.add(node.obj);
            inOrder(node.right, list);
        }
    }

    public LinkedList<Object> inOrder() {
        LinkedList<Object> list = new LinkedList<Object>();
        inOrder(this.root, list);
        return list;
    }

    // rebalance the tree after deleting or inserting a node
    private Node rebalance(Node node) {
        if (getHeight(node.right) - getHeight(node.left) == 2) {
            Node R = node.right;
            if (getHeight(R.left) > getHeight(R.right)) {
                node = doubleRotateWithRightParent(node);
            } else {
                node = rotateWithRightChild(node);
            }
        } else if (getHeight(node.left) - getHeight(node.right) == 2) {
            Node L = node.left;
            if (getHeight(L.right) > getHeight(L.left)) {
                node = doubleRotateWithLeftParent(node);
            } else {
                node = rotateWithLeftChild(node);
            }
        }
        return node;
    }

    // Update height
    private void updateHeight(Node node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

}
