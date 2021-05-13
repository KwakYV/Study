public class Dog extends Animal{
    static int count;
    public  Dog(String name){
        super(name);
        Dog.count += 1;
    }

    @Override
    public void run(int length) {
        if (length <= 500)
            System.out.println(this.name  + "пробежала - " + length + " м");
        else
            System.out.println(this.name + " пробежала - 500 м");
    }

    @Override
    public void swim(int length) {
        if (length <= 10)
            System.out.println(this.name + "проплыл " + length + " м");
        else
            System.out.println(this.name + "проплыл 10 м" );
    }
}
