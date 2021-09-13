package geek.java3.lesson6;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lesson6 {
    private final static int POINT = 4;
    private final static int ONE = 1;

    public static int[] newArray(int[] source){
        int lastPosition = 0;
        for (int i = 0; i < source.length; i++){
            if (source[i] == POINT){
                lastPosition = i;
            }
        }

        if (lastPosition == 0){
            throw new RuntimeException();
        }
        return Arrays.copyOfRange(source, lastPosition + 1, source.length);
    }


    public static boolean check(int[] target){
        List<Integer> list = Arrays.stream(target).boxed().collect(Collectors.toList());
        int  exceptSize = list.stream().filter(value -> value != POINT && value != ONE).collect(Collectors.toList()).size();
        return list.contains(POINT) && list.contains(ONE) && exceptSize == 0;
    }
}
