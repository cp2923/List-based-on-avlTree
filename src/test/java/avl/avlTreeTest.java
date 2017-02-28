package avl;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

//This is for testing the correctness of avlTree data structure

/**
 * Created by chang on 2/27/17.
 */
public class avlTreeTest {
    //insert, double rotate with left son and right grand son
    /*
       a       a            a           c
      /        /            /         /  \
     b     => b     =>   c     =>   b    a
               \         /
                c      b
    */

    @Test
    public void insert() throws Exception {
        avlTree tree = new avlTree();
        tree.insert(0,'a');
        tree.insert(0,'b');
        tree.insert(1,'c');
        Assert.assertEquals('b', tree.search(0).getObj());
        Assert.assertEquals('c', tree.search(1).getObj());
        Assert.assertEquals('a', tree.search(2).getObj());
        Assert.assertEquals(1, tree.getHeight(tree.search(1)));
    }

    //insert, double rotate with right son and left grand son
    /*
       a       a        a           c
       \        \        \         /  \
        b  =>    b  =>    c  =>   a    b
                /         \
               c           b
    */

    @Test
    public void insert2() throws Exception {
        avlTree tree = new avlTree();
        tree.insert(0,'a');
        tree.insert(1,'b');
        tree.insert(1,'c');
        Assert.assertEquals('a', tree.search(0).getObj());
        Assert.assertEquals('c', tree.search(1).getObj());
        Assert.assertEquals('b', tree.search(2).getObj());
        Assert.assertEquals(1, tree.getHeight(tree.search(1)));
    }

    /*
      b          b            d
     / \          \          / \
    a   d    =>    d    =>  b   e
       / \        / \        \
      c   e      c   e        c
     */
    // a deleting test
    @Test
    public void delete() throws Exception {
        avlTree tree = new avlTree();
        tree.insert(0,'a');
        tree.insert(1,'b');
        tree.insert(2,'c');
        tree.insert(3,'d');
        tree.insert(4,'e');
        tree.delete(0);

        Assert.assertEquals('b', tree.search(0).getObj());
        Assert.assertEquals('c', tree.search(1).getObj());
        Assert.assertEquals('d', tree.search(2).getObj());
        Assert.assertEquals('e', tree.search(3).getObj());
        Assert.assertEquals(2, tree.getHeight(tree.search(2)));
    }

}