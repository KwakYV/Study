package geek.java3.lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class RunRace {
    public static final int CARS_COUNT = 4;
    final static CountDownLatch CDL_PREPARE = new CountDownLatch(CARS_COUNT);
    final static CountDownLatch CDL_FINISH = new CountDownLatch(CARS_COUNT);
    final static CyclicBarrier BARRIER = new CyclicBarrier(CARS_COUNT);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60, false), new Tunnel(2), new Road(40, true));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), CDL_PREPARE, CDL_FINISH, BARRIER);
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        CDL_PREPARE.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        CDL_FINISH.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
