package geek.java3.lesson5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Road extends Stage{
    private boolean isFirst;
    private Lock lock;
    public Road(int length, boolean isLast) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.isLast = isLast;
        this.isFirst = false;
        lock = new ReentrantLock();
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            if (isLast && !isFirst){
                try{
                    lock.lock();
                    isFirst = true;
                    System.out.println(c.getName() + " закончил этап: " + description + " и стал победителем!!!!");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }else{
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
