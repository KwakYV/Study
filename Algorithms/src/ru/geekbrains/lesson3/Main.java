package ru.geekbrains.lesson3;

import ru.geekbrains.lesson4.LinkedDeque;

public class Main {
    public static void main(String[] args) {
        /**
         * First task with array
         */
        int[] testArr = {1, 2 ,3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16};
        showAbsentValue(testArr);

        /**
         * Deque implementation tests
         */
        System.out.println("===============Deque test===============");
//        DequeImpl deque = new DequeImpl(5);
        Deque deque = new LinkedDeque();
        System.out.println(deque.insertLeft(45));
        System.out.println(deque.insertLeft(55));
        System.out.println(deque.insertLeft(65));
        System.out.println(deque.insertRight(10));
        System.out.println(deque.insertRight(11));
        System.out.println(deque.insertRight(11));
        System.out.println(deque);
        deque.removeRight();
        System.out.println(deque);
        deque.removeLeft();
        System.out.println(deque);
        System.out.println(deque.insertLeft(45));
        System.out.println(deque);

        /**
         *  Test for Queue.
         */
        System.out.println("===========Queue test==============");
        QueueImpl queue = new QueueImpl(3);
        System.out.println(queue.insert(1));
        System.out.println(queue.insert(2));
        System.out.println(queue.insert(3));
        queue.display();
        System.out.println(queue.peekFront());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.peekFront());
        System.out.println(queue.insert(5));
        System.out.println(queue.insert(6));
        System.out.println(queue.remove());
        queue.display();
        System.out.println(queue.peekFront());
        queue.insert(8);
        queue.display();

    }

    public static void  showAbsentValue(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i+1){
                System.out.println(arr[i] - 1);
                break;
            }
        }
    }



}


