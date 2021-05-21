/**
 * Entry point
 */
public class TestLesson1 {
    public static void main(String[] args) {
        Trackable[] participants = new Trackable[3];
        Finishable[] tasks = new Finishable[2];

        Human man = new Human(150, 41);
        Cat cat = new Cat(100, 2);
        Robot robot = new Robot(50, 5);
        participants[0] = man;
        participants[1] = cat;
        participants[2] = robot;

        Wall wall = new Wall(100);
        Track track = new Track(10);
        tasks[0] = track;
        tasks[1] = wall;

        for (Trackable part: participants
             ) {
            for (Finishable task : tasks
                 ) {
                if (!part.isOutOfGame())
                    task.passOver(part);
            }
        }
    }
}
