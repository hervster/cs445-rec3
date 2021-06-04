package cs445.rec3;

/**
 * A class that implements the ADT bag using a chain of linked nodes.
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison III
 * @version 4.1
 */
public final class LinkedBag<E> implements BagInterface<E> {

    private Node head;
    private int size;

    public LinkedBag() {
        head = null;
        size = 0;
    }

    /**
     * Sees whether this bag is empty.
     * @return  True if this bag is empty, or false if not.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Gets the current number of entries in this bag.
     * @return  The integer number of entries currently in this bag.
     */
    @Override
    public int getCurrentSize() {
        return size;
    }

    /**
     * Adds a new entry to this bag.
     * @param newEntry  The object to be added as a new entry.
     * @return  True.
     */
    @Override
    public boolean add(E newEntry) {
        Node newNode = new Node(newEntry, head);
        head = newNode;
        size++;
        return true;
    }

    /**
     * Retrieves all entries that are in this bag.
     * @return  A newly allocated array of all the entries in this bag.
     */
    @Override
    public E[] toArray() {
        // the cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        E[] result = (E[]) new Object[size]; // Unchecked cast
        int index = 0;
        Node currentNode = head;
        while ((index < size) && (currentNode != null)) {
            result[index] = currentNode.data;
            index++;
            currentNode = currentNode.next;
        }
        return result;
    }

    /**
     * Counts the number of times a given entry appears in this bag.
     * @param anEntry  The entry to be counted.
     * @return  The number of times anEntry appears in this bag.
     */
    @Override
    public int getFrequencyOf(E anEntry) {
        int frequency = 0;
        int loopCounter = 0;
        Node currentNode = head;
        while ((loopCounter < size) && (currentNode != null)) {
            if (anEntry != null && anEntry.equals(currentNode.data)) {
                frequency++;
            }
            loopCounter++;
            currentNode = currentNode.next;
        }
        return frequency;
    }

    /**
     * Tests whether this bag contains a given entry.
     * @param anEntry  The entry to locate.
     * @return  True if this bag contains anEntry, or false otherwise.
     */
    @Override
    public boolean contains(E anEntry) {
        boolean found = false;
        Node currentNode = head;
        while (!found && (currentNode != null)) {
            if (anEntry != null && anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return found;
    }

    /**
     * Removes one occurrence of a given entry from this bag, if possible.
     * @param anEntry  The entry to be removed.
     * @return  True if the removal was successful, or false if not.
     */
    @Override
    public boolean remove(E anEntry) {
        boolean result = false;
        Node nodeN = getReferenceTo(anEntry);
        if (nodeN != null) {
            nodeN.data = head.data; // Teplace located entry with entry
            // in first node
            head = head.next; // Remove first node
            size--;
            result = true;
        }
        return result;
    }

    // Locates a given entry within this bag.
    // Returns a reference to the node containing the entry, if located,
    // or null otherwise.
    private Node getReferenceTo(E anEntry) {
        boolean found = false;
        Node currentNode = head;
        while (!found && (currentNode != null)) {
            if (anEntry != null && anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    /**
     * Removes all entries from this bag.
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    }

    private class Node {
        private E data; // Entry in bag
        private Node next; // link to next node

        private Node(E dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;
        }
    }

    /**
     * Removes one unspecified entry from this bag, if possible.
     * @return  Either the removed entry, if the removal was successful, or
     * null.
     */
    @Override
    public E remove() {
        E result = null;

        if (head != null) {
            result = head.data;
            head = head.next;
            size--;
        }

        return result;
    }

    /**
     * Override the toString() method so that we get a more useful display of
     * the contents in the bag.
     * @return a string representation of the contents of the bag
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Bag[ ");

        Node scout = head;
        for (int index = 0; index < size; index++) {
            result.append(scout.data.toString());
            result.append(' ');
            scout = scout.next;
        }

        result.append(']');
        return result.toString();
    }

    /**
     * Determines if two bags are equal.
     * @param other Another object to check this bag against.
     * @return True if other is an LinkedBag containing the same objects, with
     * the same frequencies, as this bag.
     */
    @Override
    public boolean equals(Object other) {
        // TODO: Rewrite this method so that it works according to the contents
        // of the bags (this and other). Be sure to consider if other is null,
        // or a type other than LinkedBag.
        //return this == other;

        if (other == null) {return false;}

        if (!(other instanceof LinkedBag)) { return false;}

        LinkedBag B_Other = (LinkedBag) other;

        if (this.getCurrentSize() != B_Other.getCurrentSize()) { return false;}

        Node current = this.head;
        while( current != null) {
            if (this.getFrequencyOf(current.data) != B_Other.getFrequencyOf(current.data)) { return false;}
            current = current.next;
        }
        return true;
    }

    /**
     * Removes all duplicates of a specific entry from this bag, leaving only
     * one instance of it.
     * @param anEntry the entry whose duplicates should be removed
     */
    @Override
    public void removeDuplicatesOf(E anEntry) {
        // TODO: Write this method so that it removes all duplicate entries of
        // the given Entry from this bag while leaving the first instance of it

        Node first = getReferenceTo(anEntry);

        // x, y, z, x, A, B
        if (first != null) {
            
            while (first.next != null)
            {
                if (anEntry.equals(first.next.data))
                {
                    first.next = first.next.next;
                    size--;
                }
                else
                {
                    first = first.next;
                }
            }
        }
    }

    /**
     * Removes all duplicate items from this bag, leaving one instance of each
     * entry.
     */
    @Override
    public void removeAllDuplicates() {
        // TODO: Write this method so that it removes all duplicate entries from
        // this bag

        Node current = this.head;

        while(current != null)
        {
            removeDuplicatesOf(current.data);
            current=current.next;
        }
    }
}

