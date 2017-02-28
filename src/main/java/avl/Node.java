/**
 * Created by chang on 2/24/17.
 */
package avl;

public class Node {
    int numberOfNodesOnLeft ;
    int height;
    Node left;
    Node right;
    Object obj;

    public Object getObj(){
        return this.obj;
    }

    public void setObj(Object newObj) {
        this.obj = newObj;
    }

    public Node(Object obj) {
        numberOfNodesOnLeft = 0;
        left = null;
        right = null;
        height = 0;
        this.obj = obj;
    }


}

