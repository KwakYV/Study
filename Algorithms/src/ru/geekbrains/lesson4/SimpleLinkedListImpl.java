package ru.geekbrains.lesson4;

import java.util.Iterator;

public class SimpleLinkedListImpl<E> implements LinkedList<E>, Iterable<E> {

    protected Node<E> first;
    protected int size;

    @Override
    public void insertFirst(E value) {
        first = new Node<>(value, first);
        if (first.next != null){
            first.next.prev = first;
        }
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<E> removerNode = first;
        first = removerNode.next;
        removerNode.next = null;
        size--;

        return removerNode.item;
    }

    @Override
    public boolean remove(E value) {
        Node<E> current = first;
        Node<E> prev = null;

        while (current != null) {
            if (current.item.equals(value)) {
                break;
            }
            prev = current;
            current = current.next;
        }

        if (current == null) {
            return false;
        } else if (current == first) {
            removeFirst();
            return true;
        }
        prev.next = current.next;

        current.next = null;
        size--;

        return true;

    }

    @Override
    public boolean contains(E value) {
        Node<E> current = first;
        while (current != null) {
            if (current.item.equals(value)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");
        Node<E> current = first;
        while (current != null) {
            sb.append(current.item);
            if (current.next != null) {
                sb.append(" -> ");
            }

            current = current.next;
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public E getFirst() {
        return first.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current_position;
        public LinkedListIterator() {
            current_position = first;
        }

        @Override
        public boolean hasNext() {
            return current_position.next != null;
        }

        @Override
        public E next() {
            if (current_position.equals(first)){
                current_position = current_position.next;
                return first.item;
            }
            E item = current_position.next.item;
            current_position = current_position.next;
            return item;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }


    }
}
