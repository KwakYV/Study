package ru.geekbrains.lesson4;

import ru.geekbrains.lesson3.Deque;

public class LinkedDeque<E> implements Deque<E> {
    private final TwoSideLinkedList<E> data;
    public LinkedDeque() {
        this.data = new TwoSideLinkedListImpl<>();
    }

    @Override
    public boolean insertLeft(E value) {
        data.insertFirst(value);
        return true;
    }

    @Override
    public boolean insertRight(E value) {
        data.insertLast(value);
        return true;
    }

    @Override
    public E removeLeft() {
        return data.removeFirst();
    }

    @Override
    public E removeRight() {
        return data.removeLast();
    }

    @Override
    public boolean insert(Object value) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peekFront() {
        return data.getFirst();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void display() {

    }
    @Override
    public String toString() {
        return "LinkedQueue{" + "data=" + data + '}';
    }
}
