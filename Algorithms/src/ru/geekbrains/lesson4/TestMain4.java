package ru.geekbrains.lesson4;

public class TestMain4 {

    public static void main(String[] args) {
        testLinkedList();
    }

    private static void testLinkedList() {
        LinkedList<Integer> linkedList = new SimpleLinkedListImpl<>();
//        var linkedList = new TwoSideLinkedListImpl<>();
        linkedList.insertFirst(1);
        linkedList.insertFirst(2);
        linkedList.insertFirst(3);
        linkedList.insertFirst(4);
        linkedList.insertFirst(5);
        linkedList.insertFirst(6);
        linkedList.insertFirst(7);
        linkedList.insertFirst(8);
//        linkedList.insertLast(9);
//        linkedList.insertLast(10);
//        linkedList.insertLast(11);

        linkedList.display();

//        System.out.println("Find 2: " + linkedList.contains(2));
//        System.out.println("Find 1: " + linkedList.contains(1));
//        System.out.println("Find 4: " + linkedList.contains(4));
//        System.out.println("Find 4444: " + linkedList.contains(4444));

//        linkedList.removeFirst();
//        linkedList.remove(4);
//        linkedList.re

        linkedList.display();


        //ДОЛЖНО РАБОТАТЬ! Iterable - LinkedListIterator impl Iterator

        for (Integer value : linkedList) {
            System.out.println("value: " + value);
        }
    }

}
