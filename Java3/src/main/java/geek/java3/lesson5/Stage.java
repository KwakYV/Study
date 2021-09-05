package geek.java3.lesson5;

public abstract class Stage {
    protected int length;
    protected String description;
    protected boolean isLast;
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c);
}
