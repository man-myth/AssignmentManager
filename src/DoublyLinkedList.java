/**
 * CS 211 Group Activity 2
 * Group Name: Sleepless Programmers
 * Assigned Application: Google Classroom
 *
 * Professor: Mam Josephine Dela Cruz
 * Due Date: Oct 6, 2021
 *
 */

//DoublyLinkedList

import java.util.NoSuchElementException;
import java.util.*;

public class DoublyLinkedList<E> implements MyList<E> {
    /**
     * Instance variables
     */
    private DoublyLinkedNode<E> header;
    private DoublyLinkedNode<E> trailer;
    private int size;

    /**
     * DoublyLinkedNode for the DoublyLinkedList
     */
    public class DoublyLinkedNode<E> {
        /**
         * Instance variables
         */
        private E data;
        private DoublyLinkedNode<E> next;
        public DoublyLinkedNode<E> previous;

        /**
         * Constructor
         */
        public DoublyLinkedNode(E data) {
            this.data = data;
        }

        /**
         * Getter Methods
         */
        public E getData() {
            return data;
        }

        public DoublyLinkedNode<E> getNext() {
            return next;
        }

        public DoublyLinkedNode<E> getPrevious() {
            return previous;
        }

        /**
         * Setter Methods
         */
        public void setNext(DoublyLinkedNode<E> next) {
            this.next = next;
        }

        public void setPrevious(DoublyLinkedNode<E> previous) {
            this.previous = previous;
        }

    }

    /**
     * Constructor for DoublyLinkedList
     */
    public DoublyLinkedList() {
        header = new DoublyLinkedNode<>(null);
        trailer = new DoublyLinkedNode<>(null);
        trailer.setPrevious(header);
        header.setNext(trailer);
    }

    /**
     * Returns the current size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the next node in the linked list
     */
    public E getNext(E e){
        DoublyLinkedNode<E> pointer = header;
        E node = null;

        for (int i = 0; i < size; i++) {
            if (pointer.getData() == e) {
                node = pointer.getNext().getData();
                break;
            }
            pointer = pointer.getNext();
        }
        return node;
    }

    /**
     * Returns the first node in the linked list
     */
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return header.getNext().getData();
    }

    /**
     * Returns the last node in the linked list
     */
    public E last() {
        if (isEmpty()) {
            return null;
        }
        return trailer.getPrevious().getData();
    }

    /**
     * Returns the size of the linked list
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds a node between two nodes in the linked list
     */
    private void addBetween(E e, DoublyLinkedNode<E> predecessor, DoublyLinkedNode<E> successor) {
        DoublyLinkedNode<E> newest = new DoublyLinkedNode<>(e);
        newest.setPrevious(predecessor);
        newest.setNext(successor);
        predecessor.setNext(newest);
        successor.setPrevious(newest);
        size++;
    }

    /**
     * Inserts a node on the head of the linked list
     */
    public void insert(E data) {
        addBetween(data, header, header.getNext());
    }

    /**
     * Inserts a node on the tail of the linked list
     */
    public void insertLast(E data) {
        addBetween(data, trailer.getPrevious(), trailer);
    }

    /**
     * Returns an element in the linked list
     */
    public E getElement(E data) throws NoSuchElementException {
        DoublyLinkedNode<E> pointer = header;
        E flag = null;
        while (pointer != null) {
            if (pointer.getData() == data) {
                flag = pointer.getData();
            }
            pointer = pointer.getNext();
        }
        return flag;
    }


    /**
     * Deletes the node on the head of the linked list
     */
    public boolean deleteFirst() {
        if (isEmpty()) {
            return false;
        } else {
            delete(header.getNext().getData());
        }
        return true;
    }


    /**
     * Deletes the node on the tail of the linked list
     */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        } else {
            delete(trailer.getPrevious().getData());
        }
        return true;
    }

    /**
     * Deletes a specific node on the linked list
     */
    public boolean delete(E e) {
        DoublyLinkedNode<E> data = null;
        if (search(e)){
            DoublyLinkedNode<E> currentNode = header;
            for (int i = 0; i < size; i++) {
                if (currentNode.getData() == e) {
                    data = currentNode;
                }
                currentNode = currentNode.getNext();
            }
            DoublyLinkedNode<E> predecessor = data.getPrevious();
            DoublyLinkedNode<E> successor = data.getNext();

            predecessor.setNext(successor);
            successor.setPrevious(predecessor);
            size--;
            return true;
        }
        return false;
    }

    /**
     * Searches for a specific node on the linked list
     */
    public boolean search(E data) {
        DoublyLinkedNode<E> pointer = header;
        boolean flag = false;
        for (int i = 0; i < size; i++) {
            if (pointer.getData() == data) {
                flag = true;
            }
            pointer = pointer.getNext();
        }
        return flag;
    }

    /**
     * toString method
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DoublyLinkedNode<E> pointer = header;
        sb.append(pointer.getData()).append(", ");
        for (int i = -1; i < size; i++) {
            pointer = pointer.getNext();
            if (i > -1) {
                sb.append(", ");
            }
            sb.append(pointer.getData());
        }
        sb.append("]");
        return sb.toString();
    }
}
