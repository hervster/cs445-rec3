package cs445.rec3;

import java.util.Arrays;

/**
 * A class that implements the ADT bag using a resizable array.
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison III
 * @version 4.1
 */
public class ArrayBag<E> implements BagInterface<E> {

    private E[] bag;
    private int size;
    // Initial capacity of bag
    private static final int DEFAULT_CAPACITY = 25;

    /**
     * Creates an empty bag whose initial capacity is 25.
    */
    public ArrayBag() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty bag having a given initial capacity.
     * @param initialCapacity  The integer capacity desired.
     */
    public ArrayBag(int initialCapacity) {
        @SuppressWarnings("unchecked")
        E[] tempBag = (E[])new Object[initialCapacity];
        bag = tempBag;
        size = 0;
    }

    /**
     * Creates this bag containing given entries.
     * @param contents  An array of objects.
     */
    public ArrayBag(E[] contents) {
        bag = Arrays.copyOf(contents, contents.length);
        size = contents.length;
    }

    /**
     * Adds a new entry to this bag.
     * @param newEntry  The object to be added as a new entry.
     * @return  True.
     */
    @Override
    public boolean add(E newEntry) {
        if (isArrayFull()) {
            doubleCapacity();
        }

        bag[size] = newEntry;
        size++;

        return true;
    }

    /**
     * Retrieves all entries that are in this bag.
     * @return  A newly allocated array of all the entries in this bag.
     */
    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] result = (E[])new Object[size]; // Unchecked cast
        for (int index = 0; index < size; index++) {
            result[index] = bag[index];
        }
        return result;
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
     * Counts the number of times a given entry appears in this bag.
     * @param anEntry  The entry to be counted.
     * @return  The number of times anEntry appears in this bag.
     */
    @Override
    public int getFrequencyOf(E anEntry) {
        int counter = 0;

        for (int index = 0; index < size; index++) {
            if (anEntry != null && anEntry.equals(bag[index])) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Tests whether this bag contains a given entry.
     * @param anEntry  The entry to locate.
     * @return  True if this bag contains anEntry, or false otherwise.
     */
    @Override
    public boolean contains(E anEntry) {
        return getIndexOf(anEntry) > -1; // or >= 0
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

    /**
     * Removes one unspecified entry from this bag, if possible.
     * @return  Either the removed entry, if the removal was successful, or
     * null.
     */
    @Override
    public E remove() {
        E result = removeEntry(size - 1);
        return result;
    }

    /**
     * Removes one occurrence of a given entry from this bag, if possible.
     * @param anEntry  The entry to be removed.
     * @return  True if the removal was successful, or false if not.
     */
    @Override
    public boolean remove(E anEntry) {
        int index = getIndexOf(anEntry);
        E result = removeEntry(index);
        return anEntry != null && anEntry.equals(result);
    }

    // Locates a given entry within the array bag.
    // Returns the index of the entry, if located,
    // or -1 otherwise.
    private int getIndexOf(E anEntry) {
        int where = -1;
        boolean found = false;
        int index = 0;

        while (!found && (index < size)) {
            if (anEntry != null && anEntry.equals(bag[index])) {
                found = true;
                where = index;
            }
            index++;
        }

        // Assertion: If where > -1, anEntry is in the array bag, and it
        // equals bag[where]; otherwise, anEntry is not in the array.

        return where;
    }

    // Removes and returns the entry at a given index within the array.
    // If no such entry exists, returns null.
    // Precondition: 0 <= givenIndex < size.
    private E removeEntry(int givenIndex) {
        E result = null;

        if (!isEmpty() && (givenIndex >= 0)) {
            // Entry to remove
            result = bag[givenIndex];
            int lastIndex = size - 1;
            // Replace entry to remove with last entry
            bag[givenIndex] = bag[lastIndex];
            // Remove reference to last entry
            bag[lastIndex] = null;
            size--;
        }

        return result;
    }

    // Returns true if the array bag is full, or false if not.
    private boolean isArrayFull() {
        return size >= bag.length;
    }

    // Doubles the size of the array bag.
    private void doubleCapacity() {
        int newLength = 2 * bag.length;
        bag = Arrays.copyOf(bag, newLength);
    }

    /**
     * Override the toString() method so that we get a more useful display of
     * the contents in the bag.
     * @return a string representation of the contents of the bag
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Bag[ ");

        for (int index = 0; index < size; index++) {
            result.append(bag[index].toString());
            result.append(' ');
        }

        result.append(']');
        return result.toString();
    }

    /**
     * Determines if two bags are equal.
     * @param other Another object to check this bag against.
     * @return True if other is an ArrayBag containing the same objects, with
     * the same frequencies, as this bag.
     */
    @Override
    public boolean equals(Object other) {
        // TODO: Rewrite this method so that it works according to the contents
        // of the bags (this and other). Be sure to consider if other is null,
        // or a type other than ArrayBag.

        // return this == other;
        if (other == null) {return false;}
        if ( !(other instanceof ArrayBag))  { return false;}

        ArrayBag B_Other = (ArrayBag) other;

        if (B_Other.getCurrentSize() != this.getCurrentSize()) { return false;}

        // Check if frequency of each method in one bag is the same as the other

        for (int i=0; i< size; i++)
        {
            E common = bag[i];
            if (this.getFrequencyOf(common) != B_Other.getFrequencyOf(common)) { return false; }
        }
        return true;
    }

    /**
     * Removes all duplicates of a specific entry from this bag, leaving only
     * one instance of it.
     * @param anEntry the entry whose duplicates should be removed
     */
    @Override
    public void removeDuplicatesOf(E anEntry){
        // TODO: Write this method so that it removes all duplicate entries of
        // the given Entry from this bag while leaving the first instance of it

        // 
        int firstIndex = getIndexOf(anEntry);
        if (firstIndex >= 0)
        {
            for (int i = firstIndex + 1; i < size; i++ )
            {
                if (anEntry.equals(bag[i])) 
                { 
                    removeEntry(i);
                    i--;
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

        for (int i = 0; i < size; i++)
        {
            removeDuplicatesOf(bag[i]);
        }
    }
}

