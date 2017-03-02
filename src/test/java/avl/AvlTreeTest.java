package avl;

import org.junit.Assert;
import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static org.junit.Assert.*;


/**
 * Created by chang on 2/27/17.
 * <p>
 * This is for testing the correctness of AvlTree data structure
 * <p>
 * In a binary tree the balance factor of a node N is defined to be the height difference of its two child subtrees.
 * A binary tree is defined to be an AVL tree if the invariant
 * BalanceFactor(N) ∈ {–1,0,+1} holds for every node N in the tree.
 */
public class AvlTreeTest {

    /* insert, double rotate with left son and right grand son
       a       a            a           c
      /        /            /         /  \
     b     => b     =>   c     =>   b    a
               \         /
                c      b
    */

    @Test
    public void insert() throws Exception {
        AvlTree tree = new AvlTree();
        tree.insert(0, 'a');
        checkInvariant(tree);
        tree.insert(0, 'b');
        checkInvariant(tree);
        tree.insert(1, 'c');
        checkInvariant(tree);

        LinkedList<Object> expected = new LinkedList<Object>(Arrays.asList('b', 'c', 'a'));
        LinkedList<Object> result = tree.inOrder();
        Assert.assertTrue(expected.equals(result));
    }

    /* insert, double rotate with right son and left grand son

       a       a       a           c
        \       \       \         / \
        b  =>    b  =>   c  =>   a   b
                /        \
               c          b
    */

    @Test
    public void insert2() throws Exception {
        AvlTree tree = new AvlTree();
        tree.insert(0, 'a');
        checkInvariant(tree);
        tree.insert(1, 'b');
        checkInvariant(tree);
        tree.insert(1, 'c');
        checkInvariant(tree);

        LinkedList<Object> expected = new LinkedList<Object>(Arrays.asList('a', 'c', 'b'));
        LinkedList<Object> result = tree.inOrder();
        Assert.assertTrue(expected.equals(result));
        Assert.assertEquals(1, tree.getHeight(tree.search(1)));
    }


    /* rotate twice

         d            d               d           c
        / \          /  \           /  \         / \
       b   e  =>    b   e   =>     c    e  =>   b   d
      / \          / \            / \          /   / \
     a   c        a   c         b   f         a  f   e
                       \       /
                       f     a
    */
    @Test
    public void insert3() throws Exception {
        AvlTree tree = new AvlTree();
        tree.insert(0, 'd');
        checkInvariant(tree);
        tree.insert(0, 'b');
        checkInvariant(tree);
        tree.insert(2, 'e');
        checkInvariant(tree);
        tree.insert(0, 'a');
        checkInvariant(tree);
        tree.insert(2, 'c');
        checkInvariant(tree);
        tree.insert(3, 'f');
        checkInvariant(tree);

        LinkedList<Object> expected = new LinkedList<Object>(Arrays.asList('a', 'b', 'c', 'f', 'd', 'e'));
        LinkedList<Object> result = tree.inOrder();
        Assert.assertTrue(expected.equals(result));
        Assert.assertEquals(2, tree.getHeight(tree.search(2)));
    }


    /* a deleting test
      b          b            d
     / \          \          / \
    a   d    =>    d    =>  b   e
       / \        / \        \
      c   e      c   e        c
    */
    @Test
    public void delete() throws Exception {
        AvlTree tree = new AvlTree();
        tree.insert(0, 'a');
        tree.insert(1, 'b');
        tree.insert(2, 'c');
        checkInvariant(tree);
        tree.insert(3, 'd');
        tree.insert(4, 'e');
        checkInvariant(tree);

        tree.delete(0);
        checkInvariant(tree);

        LinkedList<Object> expected = new LinkedList<Object>(Arrays.asList('b', 'c', 'd', 'e'));
        LinkedList<Object> result = tree.inOrder();
        Assert.assertTrue(expected.equals(result));
    }

    /*
            __a___               ___a___                 ___a___                   ____c____
           /       \            /       \               /       \                 /         \
          b         c          b         c             e         c              _a_          g
         / \       / \          \       / \           / \       / \            /   \        / \
        d   e     f   g     =>   e     f   g      => b   h     f   g     =>   e     f      j   k
             \   /   / \          \   /   / \                 /   / \        / \   /            \
             h  i   j   k          h i   j   k               i   j   k      b   h i              l
                         \                    \                       \
                          l                    l                       l
     */
    @Test
    public void delete2() throws Exception {
        AvlTree tree = new AvlTree();
        tree.insert(0, 'a');
        tree.insert(0, 'b');
        tree.insert(2, 'c');
        tree.insert(0, 'd');
        tree.insert(2, 'e');
        checkInvariant(tree);
        tree.insert(4, 'f');
        tree.insert(6, 'g');
        tree.insert(3, 'h');
        tree.insert(5, 'i');
        tree.insert(8, 'j');
        tree.insert(10, 'k');
        tree.insert(11, 'l');
        checkInvariant(tree);

        tree.delete(0);
        checkInvariant(tree);

        Assert.assertEquals('i', tree.search(4).getObj());

        LinkedList<Object> expected = new LinkedList<Object>(Arrays.asList('b', 'e', 'h', 'a', 'i', 'f', 'c', 'j', 'g', 'k', 'l'));
        LinkedList<Object> result = tree.inOrder();
        Assert.assertTrue(expected.equals(result));
    }

    //random add and delete
    @Test
    public void randomTest() throws Exception {
        AvlTree tree = new AvlTree();
        tree.insert(0, null);
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            int randomNum = rand.nextInt(tree.getSize());
            tree.insert(randomNum, null);
            checkInvariant(tree);
        }
        for (int i = 0; i < 10000; i++) {
            int randomNum = rand.nextInt(tree.getSize());
            tree.delete(randomNum);
            checkInvariant(tree);
        }
    }

    // test search out of range
    @Test(expected = IndexOutOfBoundsException.class)
    public void searchIndexOutOfBoundsException() {
        AvlTree tree = new AvlTree();
        tree.insert(0, null);
        tree.search(2);
    }


    /*
    helper function
     */

    //check invariant
    private void checkInvariant(AvlTree tree) {
        for (int i = 0; i < tree.getSize(); i++) {
            Node node = tree.search(i);
            Assert.assertTrue(abs(tree.getHeight(node.left) - tree.getHeight(node.right)) < 2);
        }
    }


}