public class Cat {
    private String name;
    private int appetite;
    private boolean isFull;
    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.isFull = false;
    }
    public void eat(Plate p) {
        if (p.getFood() >= appetite){
            p.decreaseFood(appetite);
            this.isFull = true;
        }else{
            this.isFull = false;
        }
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", appetite=" + appetite +
                ", isFull=" + isFull +
                '}';
    }
}
