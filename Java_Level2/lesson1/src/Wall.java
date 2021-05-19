public class Wall implements Finishable{
    private int height;

    public Wall(int height){
        this.height = height;
    }

    public void passOver(Trackable participant){
        participant.jump(this.height);
    }
}
