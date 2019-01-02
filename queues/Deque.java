/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        deque.addFirst("h");
        deque.addLast("i");
        deque.addLast("i");
        deque.addLast("h");
        deque.removeFirst();
        deque.removeFirst();

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());

        System.out.println(deque.isEmpty());

        deque.addFirst("h");

        System.out.println(deque.isEmpty());
    }

    private int size;
    private Node head;
    private Node tail;

    // construct an empty deque
    public Deque() {
        size = 0;
        Node node = new Node();
        head = node;
        tail = node;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node() {

        }

        public Node(Item item) {
            this.item = item;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head.equals(tail);
    }
    // return the number of items on the deque
    public int size() {
        return size;
    }
    // add the item to the front
    public void addFirst(Item item) {
        if(item == null) throw new IllegalArgumentException();
        
        Node newNode = new Node(item);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }
    // add the item to the end
    public void addLast(Item item) {
        if(item == null) throw new IllegalArgumentException();

        Node newNode = new Node(item);
        newNode.prev = tail;

        tail.next = newNode;
        tail = newNode;
        size++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = head.item;
        head = head.next;
        head.prev = null;
        size--;

        return item;
    }
    // remove and return the item from the end
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = tail.item;
        tail = tail.prev;
        tail.next = null;
        size--;

        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node node = head;

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return node != null && node.next != null;
            }

            @Override
            public Item next() {
                if(!hasNext()) throw new NoSuchElementException();

                Item item = node.item;
                node = node.next;

                return item;
            }
        };
    }
}
