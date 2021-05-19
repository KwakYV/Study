public class Animal {
    protected String name;
    static int count;

    public Animal(String name) {
        this.name = name;
        Animal.count += 1;
    }

    public void run(int length){
        System.out.println("Animal can run - " + length);
    }

    public void swim(int length){
        System.out.println("Animal can swim - " + length);
    }
}
