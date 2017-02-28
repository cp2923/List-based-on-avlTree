package myList; /**
 * Created by chang on 2/24/17.
 */
import avl.Node;
import avl.avlTree;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class myList implements List{

    private avlTree tree = new avlTree();

    public int size() {
        return tree.getSize();
    }

    public boolean isEmpty() {
        if (tree.getSize() ==0 ){
            return true;
        }
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public boolean add(Object o) {
        tree.insert(tree.getSize(), o);
        return true;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean addAll(Collection c) {
        return false;
    }

    public boolean addAll(int index, Collection c) {
        return false;
    }

    public void clear() {

    }

    public Object get(int index) {
        Node node = tree.search(index);
        return node.getObj();
    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public Object set(int index, Object element) {
        Node node = tree.search(index);
        Object tmp = node.getObj();
        node.setObj(element);
        return tmp;
    }

    public void add(int index, Object element) {
        tree.insert(index, element);
    }

    public Object remove(int index) {
        return tree.delete(index);
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator listIterator() {
        return null;
    }

    public ListIterator listIterator(int index) {
        return null;
    }

    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    // not required but useful for primitive tests
    public void print(){
        tree.inOrder();
    }
}

