
/**
 * Created by chang on 2/24/17.
 */
package avl;

public class avlTree {
    public Node root;
    private int size;

    public int getSize(){

        return size;
    }

    public avlTree() {
        root = null;
        size = 0;
    }

    // search n-th node
    public Node search (int n){
        if (n > size -1 || n < 0){
            throw new IndexOutOfBoundsException("index is out of range");
        } else {
            Node point = root;

            // index of current point
            int i = point.numberOfNodesOnLeft;

            while (i != n) {
                //since index is larger than n, should go left
                if (i > n) {
                    int tmp = point.numberOfNodesOnLeft;
                    point = point.left;
                    i = i - tmp + point.numberOfNodesOnLeft;
                } else if (i < n) { //go right
                    point = point.right;
                    i = i + point.numberOfNodesOnLeft + 1;
                }
            }
            return point;
        }
    }

    // insertion
    private Node recursiveInsert(Node newNode, Node parent, int nIndex, int leftBase) { //newNode's Index


        Node newParent = parent;  // root of subtree parent

        // index of parent
        int pIndex = leftBase + parent.numberOfNodesOnLeft;

        if ( nIndex <= pIndex) {
            parent.numberOfNodesOnLeft += 1;
            if (parent.left == null) {
                parent.left = newNode;  //attach new node as leaf
            } else {

                parent.left = recursiveInsert(newNode, parent.left, nIndex, leftBase);   // branch left
                if ((getHeight(parent.left) - getHeight(parent.right)) == 2) {

                    if (nIndex < parent.left.numberOfNodesOnLeft + leftBase) {
                        newParent = rotateWithLeftChild(parent);
                    } else {
                        newParent = doubleRotateWithLeftParent(parent);
                    }
                }
            }
        } else if (nIndex > pIndex) {

            leftBase = pIndex + 1;
            if (parent.right == null) {
                parent.right = newNode;   // attach new node as leaf
            } else {
                parent.right = recursiveInsert(newNode, parent.right, nIndex, leftBase);  // branch right

                if ((getHeight(parent.right) - getHeight(parent.left)) == 2) {

                    if (nIndex > parent.right.numberOfNodesOnLeft + leftBase) {
                        newParent = rotateWithRightChild(parent);

                    } else {
                        newParent = doubleRotateWithRightParent(parent);
                    }
                }
            }
        }
        // Update height

        if ((parent.left == null) && (parent.right != null)){

            parent.height = parent.right.height + 1;

        } else if ((parent.right == null) && (parent.left != null)) {

            parent.height = parent.left.height + 1;

        } else {

            parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) + 1;

        }
        return newParent; // return new root of this subtree

    }

    public void insert(int i, Object obj) {
        // Since this avlTree is modified for List
        if (i > size || i < 0){
            throw new IndexOutOfBoundsException("index is out of range");
        } else {
            size += 1;
        }

        Node newNode = new Node(obj);
        // index of its parent + 1
        int leftBase = 0;
        if(root == null) {
            root = newNode;
        } else {
            root = recursiveInsert(newNode, root, i, leftBase);

        }
    }

    // recursive delete
    private Node remove(Node parent, int dIndex, int leftBase) {

        int pIndex = leftBase + parent.numberOfNodesOnLeft;

        if (dIndex < pIndex) {        // the node is in left side
            parent.numberOfNodesOnLeft -= 1;
            parent.left = remove(parent.left, dIndex, leftBase);

            // rebalance
            if (getHeight(parent.right) - getHeight(parent.left) == 2) {

                Node R =  parent.right;
                if (getHeight(R.left) > getHeight(R.right))
                    parent = doubleRotateWithRightParent(parent);
                else
                    parent = rotateWithRightChild(parent);
            }
        } else if (dIndex > pIndex) {    // the node is in right side

            leftBase = pIndex + 1;

            parent.right = remove(parent.right, dIndex, leftBase);
            // rebalance
            if (getHeight(parent.left) - getHeight(parent.right) == 2) {
                Node L =  parent.left;
                if (getHeight(L.right) > getHeight(L.left))
                    parent = doubleRotateWithLeftParent(parent);
                else
                    parent = rotateWithLeftChild(parent);
            }
        } else {
            //parent is the node would be removed
            if ((parent.left!=null) && (parent.right!=null)) {
                if (getHeight(parent.left) > getHeight(parent.right)) {
                    // switch the object of the most right descendant of left child and the object of the node
                    // Thus reduce consumption of switching
                    Node lMax = getMax(parent.left);
                    parent.numberOfNodesOnLeft -= 1;

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
                deleted = parent.obj;
                Node tmp = parent;
                if (parent.left != null){
                    parent = parent.left;
                } else {
                    parent = parent.right;
                }
                tmp = null;
            }
        }
        // calculate the height again
        if (parent != null) {
            parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) + 1;
        }
        return parent;
    }

    // help return the obj which needed to be delete

    public Object deleted = null;
    public Object delete (int i) {
        deleted = null;
        // Since this avlTree is modified for List
        if (i > size -1 || i < 0){
            throw new IndexOutOfBoundsException("index is out of range");
        } else {
            size -= 1;
            int leftBase = 0;
            root = remove(root, i, leftBase);
        }
        return deleted;
    }

    /*
    avlTree helper functions
    */

    //height of subtree rooted at x
    public int getHeight(Node x) {
        if (x == null) {
            // -1 + 1 = 0, which would be very helpful for calculating the height
            return -1;
        } else {
            return x.height;
        }
    }

    private Node rotateWithLeftChild (Node P) {

        Node L = P.left; // left child of P
        // 1 for L
        P.numberOfNodesOnLeft = P.numberOfNodesOnLeft - L.numberOfNodesOnLeft - 1;

        P.left = L.right;
        L.right = P;

        P.height = Math.max(getHeight(P.left), getHeight(P.right)) + 1;
        L.height = Math.max(getHeight(L.left), getHeight(L.right)) + 1;

        return L;

    }

    private Node rotateWithRightChild (Node P) {

        Node R = P.right; // right child of P
        // 1 for P
        R.numberOfNodesOnLeft = R.numberOfNodesOnLeft + P.numberOfNodesOnLeft + 1;

        P.right = R.left;
        R.left = P;

        P.height = Math.max(getHeight(P.left), getHeight(P.right)) + 1;
        R.height = Math.max(getHeight(R.left), getHeight(R.right)) + 1;

        return R;
    }

    //parent rotate with right child then grand rotate with left child
    private Node doubleRotateWithLeftParent (Node G) {
        // G for grand
        G.left = rotateWithRightChild(G.left);

        return rotateWithLeftChild(G);

    }

    //parent rotate with left child then grand rotate with right child
    private Node doubleRotateWithRightParent (Node G) {
        // G for grand
        G.right = rotateWithLeftChild(G.right);

        return rotateWithRightChild(G);

    }

    // return the most right descendant of the node
    private Node getMax(Node node) {
        if (node == null) {
            return null;
        }

        while(node.right != null) {
            node = node.right;
        }
        return node;
    }

    // return the most left descendant of the node
    private Node getMin(Node node) {
        if (node == null) {
            return null;
        }

        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    //In-Order Traversal
    private void inOrder(Node node) {
        if(node != null)
        {
            inOrder(node.left);
            System.out.print(node.obj + " ");
            inOrder(node.right);
        }
    }

    public void inOrder() {
        inOrder(root);
    }

}
