package ru.geekbrains.lesson4;

public class TwoSideLinkedListImpl<E> extends SimpleLinkedListImpl<E> implements TwoSideLinkedList<E> {

    protected Node<E> last;

    @Override
    public void insertFirst(E value) {
        super.insertFirst(value);
        if (size == 1) {
            last = first;
        }
    }

    @Override
    public void insertLast(E value) {
        Node<E> newNode = new Node<>(value, null);

        if (isEmpty()) {
            insertFirst(value);
            return;
        }

        last.next = newNode;
        newNode.prev = last;
        last = newNode;
        size++;
    }

    @Override
    public E removeLast() {
        if (isEmpty()){
            return null;
        }

        last = last.prev;
        last.next = null;
        return last.item;
    }

    @Override
    public E removeFirst() {
        E removedValue = super.removeFirst();

        if (isEmpty()) {
            last = null;
        }

        return removedValue;
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
        } else if(current == last) {
            last = prev;
            last.next = null;
        }
        prev.next = current.next;

        current.next = null;
        size--;

        return true;
    }

    @Override
    public E getLast() {
        return last.item;
    }
}
