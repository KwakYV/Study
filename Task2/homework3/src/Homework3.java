import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.Random;

public class Homework3 {
    public static void main(String[] args) {
        int [] zeros_ones = new int[10];
        Random rand = new Random();
        for (int i = 0; i < zeros_ones.length; i++) {
            zeros_ones[i] = rand.nextInt(2);
        }

        switch_ones(zeros_ones);
        System.out.println(Arrays.toString(fill_array()));
        System.out.println(Arrays.toString(multiply()));
        int[][] diags = diagonals(5);
        for (int i = 0; i < diags.length; i++) {
            System.out.println(Arrays.toString(diags[i]));
        }

        System.out.println(Arrays.toString(initArray(4, 10)));

        int[] task6 = {1,2,3,6,4,7,56,-3,0};
        extremums(task6);

        int[] task7 = {1, 10, 2, 1, 2, 2, 2, 2};
        System.out.println();
        System.out.println(checkBalance(task7));

        int[] task8 = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(shift(3, task8)));
        int[] task8_1 = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(shift(-3, task8_1)));
    }

    /**
     * Задание 1 - переключение 1 и 0
     * @param arr
     */
    private static void switch_ones(int[] arr){
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1)
                arr[i] = 0;
            else
                arr[i] = 1;
        }

        System.out.println(Arrays.toString(arr));
    }

    /**
     * Задание 2 - наполнение массива
     * @return
     */
    private static int[] fill_array(){
        int[] res = new int[100];
        for (int i = 0; i < 100; i++) {
            res[i] = i + 1;
        }
        return res;
    }

    /**
     * Задание 3 - умножение элемента массива на 2 по условию.
     * @return
     */
    private static int[] multiply(){
        int[] res = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };
        for (int i = 0; i < res.length; i++) {
            if (res[i] < 6)
                res[i] = res[i] * 2;
        }
        return res;
    }

    /**
     * Задание 4 - Диагональные 1
     * @param n
     * @return
     */
    private static int[][] diagonals(int n){
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == j) || (i == n-1-j))
                    res[i][j] = 1;
            }
        }
        return res;
    }

    /**
     * Задание 5 - Инициализация массива
     * @param len
     * @param initValue
     * @return
     */
    private static int[] initArray(int len, int initValue){
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = initValue;
        }
        return res;
    }

    /**
     * Задание 6 - минимальный и максимальный элемент массива
     * @param arr
     */
    private static void extremums(int[] arr){
        if (arr.length == 0)
            System.out.println("There are no min and max");
        else{
            System.out.println(Arrays.toString(arr));
            int min = arr[0];
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] < min)
                    min = arr[i];
                if (arr[i] > max)
                    max = arr[i];
            }
            System.out.printf("Min = %d, Max = %d", min, max);
        }

    }

    /**
     * Задание 7 - Проверка равенства суммы левой и правой частей, если таковая есть.
     * @param arr
     * @return
     */
    private static boolean checkBalance(int[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum = sum + arr[i];
        }
        boolean isPossible = sum%2 == 0;

        if (isPossible) {
            int half = sum/2;
            int check = 0;
            for (int i = 0; i < arr.length; i++) {
                if (check < half)
                    check = check + arr[i];
                else if (check == half)
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    private static int[] shift(int n, int[] arr){
        int steps = 0;
        // If shifts equal length of array then no need shift elements of array
        if (Math.abs(n) == arr.length)
            return arr;

        if (Math.abs(n) > arr.length)
            steps = n % arr.length;

        else
            steps = n;

        int cnt = 0;
        int start = arr[0];
        int position = 0;
        int prev_val = 0;
        while (cnt != arr.length){
            position = position + steps;

            if (position >= arr.length)
                position = position - arr.length;

            if (position < 0)
                position = position + arr.length;

            prev_val = arr[position];
            arr[position] = start;
            start = prev_val;
            cnt++;
        }
        return arr;
    }
}
