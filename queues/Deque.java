/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(9);
        deque.addLast(10);
        deque.addFirst(4);
        deque.addFirst(5);

        for(Integer i : deque)
            System.out.println(i);
    }

    private int size;
    private Node head;
    private Node tail;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null) throw new IllegalArgumentException();
        
        Node newNode = new Node(item);

        size++;

        if(head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    // add the item to the end
    public void addLast(Item item) {
        if(item == null) throw new IllegalArgumentException();

        Node newNode = new Node(item);

        size++;

        if(tail == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        newNode.prev = tail;

        tail.next = newNode;
        tail = newNode;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = head.item;
        head = head.next;

        if(head != null) {
            head.prev = null;
        } else {
            tail = null;
        }

        size--;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = tail.item;
        tail = tail.prev;

        if(tail != null) {
            tail.next = null;
        } else {
            head = null;
        }

        size--;

        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            Node node = head;

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Item next() {
                if(!hasNext()) throw new ArrayIndexOutOfBoundsException();

                Item item = node.item;
                node = node.next;

                return item;
            }
        };
    }
}
