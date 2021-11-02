package com.geekbrains.model;

public interface Tree<T extends Comparable<? super T>> {
    boolean add(T value);
    boolean remove(T value);
    boolean contains(T value);
    int size();
}
