import java.util.Arrays;

public class TestExceptions {
    private static final int SIZE = 4;
    public static void main(String[] args) {
        System.out.println("Lesson 2. Exceptions.");
        String[][] test1 = new String[4][3];
        String[][] test2 = {{"1","2","3","4"},
                {"5","6","7","8"},
                {"9","9","9","9"},
                {"4","4","4","4"}};

        String[][] test3 = {{"1","2","3","4"},
                {"5","6","7","8"},
                {"9","9","9"},
                {"4","4","4","4"}};

        String[][] test4 = {{"1","2","3","4"},
                {"5","6","7","lkj"},
                {"9","9","9", "4"},
                {"4","4","4","4"}};

        try{
//            int sum = sumElements(test1);
//            int sum = sumElements(test2);
//            int sum = sumElements(test3);
            int sum = sumElements(test4);
            System.out.println(sum);
        }catch(MyArraySizeException e){
            System.out.println(e.getMessage());
        }catch(MyArrayDataException e){
            System.out.println(e.getMessage());
        }
    }

    public static int sumElements(String[][] arr) throws MyArraySizeException, MyArrayDataException{
        if (arr.length != SIZE){
            throw new MyArraySizeException("First dimension in array is not equal 4");
        }
        for (int i = 0; i < SIZE; i++) {
            if (arr[i].length != SIZE){
                throw new MyArraySizeException("Second dimension is not equals 4 in row - " + i);
            }
        }
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                try{
                    sum = sum + Integer.parseInt(arr[i][j]);
                } catch(NumberFormatException e){
                    throw new MyArrayDataException("There is an error in parsing element - " + arr[i][j] + "in row " + i + " and column - " + j);
                }
            }
        }
        return sum;
    }
}
