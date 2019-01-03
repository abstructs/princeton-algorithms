import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);
        queue.enqueue(10);

        for (Integer i : queue) {
            System.out.println(i);
        }
    }

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int size) {
        if(size < 1) return;

        Item[] buffer = (Item[]) new Object[size];

        for(int i = 0; i < size; i++) {
            if(i >= items.length) break;

            buffer[i] = items[i];
        }

        items = buffer;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null) throw new IllegalArgumentException();

        if(size >= items.length) {
            resize(items.length * 2);
        }

        items[size++] = item;
    }

    private int getRandomIndex() {
        return StdRandom.uniform(size);
    }

    private void swap(int index1, int index2) {
        Item temp = items[index1];
        items[index1] = items[index2];
        items[index2] = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();

        int randomIndex = getRandomIndex();
        int lastItemIndex = size - 1;

        Item item = items[randomIndex];

        if(randomIndex != lastItemIndex)
            swap(randomIndex, lastItemIndex);

        if(((double) size) / items.length == 0.25) {
            resize(items.length / 2);
        }

        size--;

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();

        return items[getRandomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        Item[] iterItems = (Item[]) new Object[size];

        for(int i = 0; i < size; i++) {
            iterItems[i] = items[i];
        }

        StdRandom.shuffle(iterItems);

        return new Iterator<Item>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Item next() {
                if(!hasNext()) throw new NoSuchElementException();

                return iterItems[index++];
            }
        };
    }
}