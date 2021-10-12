package ru.geekbrains.lesson3;

public class QueueImpl<E> implements Queue<E>{

    protected final E[] data;
    protected int size;

    protected int tail;
    protected int head;

    private final int HEAD_DEFAULT = 0;
    private final int TAIL_DEFAULT = -1;

    public QueueImpl(int maxSize) {
        this.data = (E[])new Object[maxSize];
        head = HEAD_DEFAULT;
        tail = TAIL_DEFAULT;
    }

    @Override
    public boolean insert(E value) {
        if (isFull()) {
            return false;
        }

        if (tail == data.length - 1) {
            tail = TAIL_DEFAULT;
        }

        data[++tail] = value;
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        E value;
        if (head == data.length -1){
            value = data[head];
            head = HEAD_DEFAULT;
        } else {
            value = data[head++];
        }
        size--;
        return value;
    }

    @Override
    public E peekFront() {
        return data[head];
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
    public boolean isFull() {
        return size == data.length;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        if (tail >= head){
            for (int i = head; i <= tail; i++) {
                sb.append(data[i]);
                if (i != tail) {
                    sb.append(", ");
                }
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                if (i + head  < data.length){
                    sb.append(data[i+head]);
                    if (i + head != tail){
                        sb.append(",");
                    }
                } else {
                    int newIdx = i + head - data.length;
                    sb.append(data[newIdx]);
                    if (newIdx != tail){
                        sb.append(",");
                    }
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
