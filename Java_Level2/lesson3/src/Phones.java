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
        return Arrays.toString(map.get(surName).toArray(arr));
    }

    public void putPhone(String surname, String phoneNumber){
        if (map.containsKey(surname)){
            ArrayList<String> value = map.get(surname);
            value.add(phoneNumber);
            map.put(surname, value);
        }else{
            String[] arr = {phoneNumber};
            map.put(surname, new ArrayList<String>(Arrays.asList(arr)));
        }
    }

    @Override
    public String toString() {
        return "Phones{" +
                "map=" + map +
                '}';
    }
}
