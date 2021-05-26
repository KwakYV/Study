import java.util.*;

public class TestCollections {
    public static void main(String[] args) {
        String[] strings = {"Hello",
                            "Bye",
                            "No",
                            "Yes",
                            "Join",
                            "Hello",
                            "Join",
                            "ok",
                            "yeah",
                            "WHATS UPP",
                            "Java",
                            "java",
                            "Java",
                            "Python",
                            "Python"
        };
        System.out.println("==================Start of 1 st task ==========================");
        /**
         * Print elements without duplicates
         */
        HashSet<String> noDuplicates = new HashSet<String>(Arrays.asList(strings));
        System.out.println(noDuplicates);
        HashMap<String, Integer> map = new HashMap<>(strings.length);

        /**
         * Count words in collection
         */
        for (String string : strings) {
            if (map.containsKey(string)){
                map.put(string, map.get(string) + 1);
            }else{
                map.put(string, 1);
            }
        }

        System.out.println(map);
        System.out.println("==================End of 1 st task ==========================");
        System.out.println("================== Start of 2nd task ==================");
        Phones phones = new Phones();
        System.out.println("Check unknown key and its value");
        System.out.println(phones.getPhone("Jordan"));
        System.out.println("Put new elements into Phones dictionary");
        phones.putPhone("Jordan", "89763453434");
        phones.putPhone("Pippen", "89763333333");
        phones.putPhone("Oneil", "8976345333455");
        phones.putPhone("James", "897634324234234");
        phones.putPhone("Jordan", "1111111111111");
        System.out.println("Current state of phone dictionary");
        System.out.println(phones);
        System.out.println("Key with several numbers");
        System.out.println(phones.getPhone("Jordan"));
        System.out.println("Key with one number");
        System.out.println(phones.getPhone("James"));


    }
}
