import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Phones {
    private HashMap<String, ArrayList<String>> map;

    public Phones(){
        map = new HashMap<>();
    }

    public String getPhone(String surName){
        List<String> list = map.get(surName);

        if (list == null){
            return null;
        }

        String[] arr = new String[list.size()];
        return map.get(surName).toString();
    }

    public void putPhone(String surname, String phoneNumber){
        ArrayList<String> value = map.getOrDefault(surname, new ArrayList<>());
        value.add(phoneNumber);
        map.put(surname, value);
    }

    @Override
    public String toString() {
        return "Phones{" +
                "map=" + map +
                '}';
    }
}
