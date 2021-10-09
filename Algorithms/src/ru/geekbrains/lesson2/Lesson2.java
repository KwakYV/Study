package ru.geekbrains.lesson2;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Lesson2 {
    public static void main(String[] args) {
        String[] names = new String[]{"Lenuvo","Asos","MacNote","Eser","Xamiou"};
        Notebook[] notes = new Notebook[100];
        for (int i = 0; i < notes.length; i++ ){
            notes[i] = new Notebook(names[(int)(Math.random()*5)],
                    getRandomValue(500, 2000, 50),
                    getRandomValue(4, 24, 4));
        }

//        bubbleSort(notes);
//        selectionSort(notes);
        insertionSort(notes);
        for (Notebook note : notes) {
            System.out.println(note);
        }

    }

    public static int getRandomValue(int start, int end, int step){
        return (int)(Math.random()*((end-start)/step +1) + 1)*step;
    }

    public static void bubbleSort(Notebook[] arr){
        for (int i = arr.length -1 ; i >= 1; i--){
            for (int j = 0; j < i;  j++) {
                Notebook temp = arr[j];
                if (arr[j].greater(arr[j+1])){
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void selectionSort(Notebook[] arr){
        for (int i = 0; i < arr.length; i++){
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min].greater(arr[j])){
                    min = j;
                }
            }
            // Меняем местами i  и min
            Notebook temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }


    public static void insertionSort(Notebook[] arr){
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            Notebook temp = arr[i];
            while (j > 0 && arr[j-1].greater(temp)){
                arr[j] = arr[j-1];
                --j;
            }
            arr[j] = temp;
        }
    }




}
