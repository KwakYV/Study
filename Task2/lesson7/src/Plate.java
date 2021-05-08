public class Plate {
    private int food;
    public Plate(int food) {
        this.food = food;
    }
    public void info() {
        System.out.println("plate: " + food);
    }

    public void decreaseFood(int n) {
        if (n <= food)
            food -= n;
    }

    public void increaseFood(int n){
        food += n;
    }

    public int getFood() {
        return this.food;
    }
}
