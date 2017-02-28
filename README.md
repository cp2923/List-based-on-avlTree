# List-based-on-avlTree
I Implement a list based on avlTree with several test case.

Since the four kind of rotations won't change the sequence of the tree under In-Order Traversal, I can take the order of In-Order Traversal as the order of my list.

Furthermore, since the height of the tree is O(log N), so the time complexity of insert, remove and search operation is O(log N).

Thus, I Implement a data structure which implements java.util.List with O(log N) time for the following methods:
add(Object obj), add(int index, Object obj), remove(int index), set(int index, Object obj)
