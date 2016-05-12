package org.aprestos.labs.snippets.impl.algo.sedgewick.queues;

public interface Queue<Item> {

    void enqueue(Item o);
    Item dequeue();
    boolean isEmpty();
    int size();
    
}
