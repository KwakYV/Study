package ru.geekbrains.lesson5;

import java.util.*;
import java.util.stream.Collectors;

public class Test5 {
    private static final int limit = 5;

    public static void main(String[] args) {
        System.out.println("==============Task with power digit================");
        int powerResult = power(5, 5);
        System.out.println("Result of power - " + powerResult);
        System.out.println("==============Task with backpack+++++++++++++++++++++");
        Belongings phone = new Belongings("iPhone", 2, 1000);
        Belongings tab = new Belongings("iPad", 4, 2000);
        Belongings pods = new Belongings("iPods", 1, 500);
        Belongings glasses = new Belongings("iGlass", 1, 3000);
        Belongings noteBook = new Belongings("Mac", 5, 4000);

        Belongings[] belongings = new Belongings[]{phone, tab, pods, glasses, noteBook};
        HashMap<String, Integer> memo = new HashMap<>();
        int maxCost = getValuableStuff(belongings, memo);
        String key = memo.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxCost))
                .map(entry -> entry.getKey()).collect(Collectors.joining());
        System.out.println(String.format("Stuff is %s and cost is %d", key, maxCost));

    }

    private static int power(int digit, int n) {
        if (n == 0){
            return 1;
        }

        if (n == 1){
            return digit;
        }

        return digit * power(digit, n-1);
    }

    private static int getValuableStuff(Belongings[] belongings, HashMap<String, Integer> memo) {
        String key = Arrays.stream(belongings).map(b -> b.getName()).reduce("", (prev, next) -> prev+"||"+next);
        if (memo.containsKey(key)){
            return memo.get(key);
        }
        if (belongings.length == 0){
            return 0;
        }
        int totalWeight = sum(belongings, true);
        int totalCost = sum(belongings, false);
        if (totalWeight > limit){
            List<Belongings[]> rotate = rotate(belongings);
            List<Integer> result = new ArrayList<>();
            for (Belongings[] item : rotate) {
                result.add(getValuableStuff(item, memo));
            }
            return getMax(result);
        } else {
            memo.put(key, totalCost);
            return totalCost;
        }
    }

    private static int getMax(List<Integer> result) {
        return Collections.max(result);
    }

    private static List<Belongings[]> rotate(Belongings[] arr) {
        List<Belongings[]> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            List<Belongings> list = new ArrayList<>(Arrays.asList(arr));
            list.remove(i);
            result.add(list.stream().toArray(Belongings[]::new));
        }
        return result;
    }

    private static int sum(Belongings[] list, boolean flag){
        int result;
        if (flag){
            result = Arrays.stream(list).map(b -> b.getWeight())
                    .reduce(0, (prev, next) -> prev + next);
        } else {
            result = Arrays.stream(list).map(b -> b.getCost())
                    .reduce(0, (prev, next) -> prev + next);
        }
        return result;
    }


}
