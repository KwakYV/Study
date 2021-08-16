public class Lesson3_1 {


    public static void main(String[] args) {
        /**
         * Task 1 - Switching 2 elements in array<T><
         */
        System.out.println("================Task1================");
        String[] arr = new String[]{"Hello", "Bye", "Adios", "Privet"};
        Arrays<String> arrStr = new Arrays<>(arr);
        System.out.println("Array Before switching: " + arrStr);
        arrStr.switchElements(0, 2);
        System.out.println("Array after Switching: " + arrStr);

        Integer[] iArr = {1, 2, 3, 4, 5};
        Arrays<Integer> arrInt= new Arrays<>(iArr);
        System.out.println("Array Before switching: " + arrInt);
        arrInt.switchElements(2,4);
        System.out.println("Array After switching: " + arrInt);
        /**
         * Task 2 - Convert array of E to ArrayList<E>
         */
        System.out.println("================Task2================");
        System.out.println(arrStr.convert());
        System.out.println(arrInt.convert());
        /**
         * Tsdk3 - Fruits and box
         */
        System.out.println("================Task3================");
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        for (int i=0; i<10; i++){
            appleBox1.add(new Apple());
            appleBox2.add(new Apple());
            orangeBox.add(new Orange());
        }

        System.out.println("Box weight of applebox1: " + appleBox1.getWeight());
        System.out.println("Box weight of applebox2: " + appleBox2.getWeight());
        System.out.println("Box weight of orangeBox: " + orangeBox.getWeight());

        System.out.println("Compare applebox1 with applebox2: " + appleBox1.compare(appleBox2));
        System.out.println("Compare applebox1 with orangebox: " + appleBox1.compare(orangeBox));
        System.out.println("Compare orangebox with applebox2: " + orangeBox.compare(appleBox2));

        System.out.println("Move apples from applebox1 to applebox2");
        appleBox1.moveFruits(appleBox2);
        System.out.println("Box weight of applebox1: " + appleBox1.getWeight());
        System.out.println("Box weight of applebox2: " + appleBox2.getWeight());
//        Next line causes compilation error, because we can not move apples to orange box
//        appleBox2.moveFruits(orangeBox);
    }
}
