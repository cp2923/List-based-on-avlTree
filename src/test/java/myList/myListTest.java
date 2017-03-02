package myList;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chang on 2/27/17.
 */

// Almost all tests are based on int (actually I tried to store a null), so I can count much easier.
// But the list can generally contain any Object.

public class myListTest {

    // 10000 insert at the begin of the list. so the list should be 9999, 9998, 9997 ....
    @Test
    public void add() throws Exception {
        myList list = new myList();
        for (int i = 0; i < 10000; i++) {
            list.add(0, i);
        }
        Object result = list.get(0);
        Assert.assertEquals(9999, result);
    }

    // after 10000 insert at the begin of the list. the 10-th object should be 9989. Then I set it to null
    @Test
    public void set() throws Exception {
        myList list = new myList();
        for (int i = 0; i < 10000; i++) {
            list.add(0, i);
        }
        Assert.assertEquals(9899, list.get(100));
        list.set(10, null);
        Assert.assertEquals(null, list.get(10));

    }

    // after 10000 insert at the end of the list . the 10-th object should still be 10.
    @Test
    public void add1() throws Exception {
        myList list = new myList();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        Assert.assertEquals(10, list.get(10));
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        Assert.assertEquals(10, list.get(10));
    }

    // after 10000 insert at the end of the list. I removed some objects
    @Test
    public void remove() throws Exception {
        myList list = new myList();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        Object deleted = list.remove(8);
        Assert.assertEquals(8, deleted);
        deleted = list.remove(10);
        Assert.assertEquals(11, deleted);
        Assert.assertEquals(52, list.get(50));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addIndexOutOfBoundsException() {
        myList list = new myList();
        list.add(0, 0);
        list.add(2, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIndexOutOfBoundsException() {
        myList list = new myList();
        list.add(0, 0);
        list.remove(2);
    }

    // I removed some objects and each of them is what I expected
    @Test
    public void removeSeveralTimes() {
        myList list = new myList();
        for (int i = 0; i < 1000; i++) {
            list.add(0, i);
        }
        for (int i = 0; i < 10; i++) {
            Object deleted = list.remove(50);
            Assert.assertEquals(948 - i, list.get(50));
        }
    }

}