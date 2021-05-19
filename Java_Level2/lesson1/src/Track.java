public class Track implements Finishable{
    private int distance;

    public Track(int distance){
        this.distance = distance;
    }

    public void passOver(Trackable participant){
        participant.run(this.distance);
    }



}
