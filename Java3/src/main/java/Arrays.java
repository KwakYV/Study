import java.util.ArrayList;

public class Arrays<E> {
    private E[] elements;

    public Arrays(E[] elements){
        this.elements = elements;
    }

    public E[] switchElements(int first, int second){
        if (first > elements.length + 1 || second > elements.length + 1){
            System.out.println("Indexes of switching elements must be in scope of array");
        } else{
            E firstValue = elements[first];
            E secondValue = elements[second];
            elements[first] = secondValue;
            elements[second] = firstValue;
        }
        return elements;
    }

    public ArrayList<E> convert(){
        ArrayList<E> result = new ArrayList<>();
        for (E item: elements
             ) {
            result.add(item);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Arrays{" +
                "elements=" + java.util.Arrays.toString(elements) +
                '}';
    }
}
