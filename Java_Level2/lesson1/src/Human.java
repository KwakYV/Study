public class Human implements Trackable{
    private int height;
    private int distance;
    private boolean out;

    public Human(int height, int distance){
        this.height = height;
        this.distance = distance;
    }


    public int getHeight() {
        return height;
    }

    public int getDistance() {
        return distance;
    }

    public void jump(int height){
        System.out.println("Human started jumping");
        out = height > this.height;
        if (!out)
            System.out.println("Human jumped over");
        else
            System.out.println("Human did not jump over and will not be allowed to next task");
    }

    public void run(int distance){
        System.out.println("Human started running");
        out = distance > this.distance;
        if (!out)
            System.out.println("Human finished distance");
        else
            System.out.println("Human did not finish distance and will not be allowed to next task");
    }

    @Override
    public boolean isOutOfGame() {
        return out;
    }



}
