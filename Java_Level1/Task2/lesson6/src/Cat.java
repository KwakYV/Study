public class Cat extends Animal{
    static int count;
    public Cat(String name){
        super(name);
        Cat.count += 1;
    }

    @Override
    public void run(int length){
        if (length <=  200)
           System.out.println(this.name + "пробежал - " + length);
        else
            System.out.println(this.name + "пробежал - 200 м");
    }

    @Override
    public void swim(int length) {
        System.out.println(this.name + "не умеют плавать");
    }
}
