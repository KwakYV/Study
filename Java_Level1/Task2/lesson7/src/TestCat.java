import java.util.Arrays;

public class TestCat {
    public static void main(String[] args) {
        Cat[] cats = new Cat[5];
        for (int i = 0; i < 5; i++) {
            Cat cat = new Cat("Cat" + (i+1), (i+1) + 5);
            cats[i] = cat;
        }
        Plate plate = new Plate(30);
        for (int i = 0; i <5; i++) {
            cats[i].eat(plate);
        }
        System.out.println("Current status of cats ==========>");
        System.out.println(Arrays.toString(cats));
        System.out.println("Current amount of food on the plate after eating");
        System.out.println(plate.getFood());
        plate.increaseFood(10);
        System.out.println("Current amount of food on the plate after increasing");
        System.out.println(plate.getFood());

        System.out.println("------------------------ Working with strings ------------------------------------------------");
        String testString = "I like Java!!!";

        printLastSymbolOfString(testString);
        System.out.println(isStartsEndsWith(testString, "!!!", false));
        System.out.println(isStartsEndsWith(testString, "I like", true));
        containsSubstring(testString, "Java");
        System.out.println(index(testString, "Java"));
        System.out.println(replace(testString, 'a', 'o'));
        System.out.println(setUpOrLow(true, testString));
        System.out.println(setUpOrLow(false, testString));
        int idx = index(testString, "Java");
        System.out.println(testString.substring(idx, idx + "Java".length()));
    }

    public static void printLastSymbolOfString(String aStr){
        int n = aStr.length();
        System.out.println(aStr.charAt(n-1));
    }

    public static boolean isStartsEndsWith(String aStr, String aSubStr, boolean isStart){
        if (isStart)
            return aStr.startsWith(aSubStr);
        else
            return aStr.endsWith(aSubStr);
    }

    public static void containsSubstring(String aStr, String aSubStr){
        System.out.println(aStr.contains(aSubStr));
    }

    public static int index(String aStr, String aSubStr){
        return aStr.indexOf(aSubStr);
    }

    public static String replace(String aStr, char aFrom, char aTo){
        return aStr.replace(aFrom, aTo);
    }

    public static String setUpOrLow(boolean isUp, String aStr){
        if (isUp)
            return aStr.toUpperCase();
        else
            return aStr.toLowerCase();
    }


}
