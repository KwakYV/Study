package geek.java3.lesson5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage{
    private Semaphore semaphore;

    public Tunnel(int quantity) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.isLast = false;
        semaphore = new Semaphore(quantity);
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
