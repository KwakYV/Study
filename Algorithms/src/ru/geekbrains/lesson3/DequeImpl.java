package ru.geekbrains.lesson3;

public class DequeImpl<E> implements Deque<E>{
    protected final E[] data;
    protected int size;
    protected int tail_left;
    protected int head_left, head_right;

    private final int HEAD_DEFAULT = 0;
    private final int TAIL_DEFAULT = -1;

    public DequeImpl(int maxSize){
        data = (E[]) new Object[maxSize];

        head_left = 0;
        tail_left = TAIL_DEFAULT;

        head_right = -1;

    }

    @Override
    public boolean insertLeft(E value) {
        if (isFull()) {
            return false;
        }
        data[++tail_left] = value;
        head_right = tail_left;
        size++;
        return true;
    }

    @Override
    public boolean insertRight(E value) {
        if (isFull()){
            return false;
        }
        data[++head_right] = value;
        tail_left = head_right;
        size++;
        return true;
    }

    @Override
    public E removeLeft() {
        if (isEmpty()){
            return null;
        }
        E value = data[head_left++];
        size--;
        return value;
    }

    @Override
    public E removeRight() {
        if (isEmpty()){
            return null;
        }
        E value = data[--head_right];
        tail_left = head_right;
        size--;
        return value;
    }

    @Override
    public boolean insert(E value) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peekFront() {
        return data[head_left];
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

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = head_left; i <= tail_left; i++) {
            sb.append(data[i]);
            if (i != head_right) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public void display() {
        System.out.println(this);
    }
}
