import geek.java3.lesson6.Lesson6;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestLesson6 {

    @Test
    public void testNewArray(){
        int[] test = {1,2,3,4,6,7};
        int[] res = {6, 7};
        int[] test2 = {1,2,3,4,6,7,4,5,6,7,8};
        int[] res2 = {5,6,7,8};
        int[] test3 = {1,2,3};

        Assertions.assertArrayEquals(res, Lesson6.newArray(test));
        Assertions.assertArrayEquals(res2, Lesson6.newArray(test2));
        Assertions.assertThrows(RuntimeException.class, ()->{Lesson6.newArray(test3);});
    }

    @ParameterizedTest
    @MethodSource("withParamsForCheck")
    public void testCheck(int[] source, boolean result){
        Assertions.assertEquals(result, Lesson6.check(source));
    }

    public static Stream<Arguments> withParamsForCheck(){
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[] {1, 1, 1, 4, 4, 1, 4, 4}, true));
        list.add(Arguments.arguments(new int[] {1, 1, 1, 1}, false));
        list.add(Arguments.arguments(new int[] {4, 4, 4, 4}, false));
        list.add(Arguments.arguments(new int[] {1, 4, 4, 1, 1, 4, 3}, false));
        return list.stream();
    }

}
