import java.util.ArrayList;

public class Box<T extends Fruit> implements Comparable<Box<?>>{
    private ArrayList<T> fruits;

    public Box(){
        fruits = new ArrayList<>();
    }

    public void add(T element){
        fruits.add(element);
    }

    public float getWeight(){
        float weight = 0;
        if (fruits.size() > 0){
            T first = fruits.get(0);
            weight = fruits.size() * first.getWeight();
        }
        return weight;
    }

    public String getFruitType(){
        String type = "unknown";
        if (fruits.size()>0){
            type = fruits.get(0).getName();
        }
        return type;
    }


    public void moveFruits(Box<T> box){
        for (T item : fruits) {
            box.add(item);
        }
        fruits.clear();
    }


    public boolean compare(Box<?> box){
        return this.getWeight() == box.getWeight();
    }

    @Override
    public int compareTo(Box<?> box) {
        return this.getFruitType().compareTo(box.getFruitType());
    }
}
