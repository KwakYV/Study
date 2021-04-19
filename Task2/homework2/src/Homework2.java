public class Homework2 {
    public static void main(String[] args) {
        System.out.println(checkSum(10, 20));
        checkNumber(-10);
        System.out.println(isPositive(24));
        printStringNTimes("hello", 5);
        System.out.println(isLeap(2020));
    }

//    Задание 1
    public static boolean checkSum(int a, int b){
        int s = a + b;
        if (s >= 10 && s <= 20)
            return true;
        else
            return false;
    }

//    Задание 2
    public static void checkNumber(int a){
        if (a >= 0)
            System.out.println("Число положитльное");
        else
            System.out.println("Число отрицательно");
    }

//    Задание 3
    public static boolean isPositive(int a){
        if (a >= 0)
            return true;
        else
            return false;
    }

//    Задание 4
    public static void printStringNTimes(String aStr, int aNum){
        for (int i = 0; i < aNum; i++ ){
            System.out.println(aStr);
        }
    }

//    Задание 5
    public static boolean isLeap(int year){
        if (year%400 == 0){
            return true;
        }
        if (year%100 == 0)
            return false;

        if (year%4 ==0 )
            return true;

        return false;
    }
}
