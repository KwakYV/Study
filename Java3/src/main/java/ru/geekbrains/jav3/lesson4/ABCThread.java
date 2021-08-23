package ru.geekbrains.jav3.lesson4;

public class ABCThread {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';


    public void printA(){
        try{
            synchronized (mon){
                for (int i = 0; i< 10; i++){
                    while (currentLetter != 'A'){
                        mon.wait();
                    }
                    System.out.println("A");
                    currentLetter = 'B';
                    mon.notifyAll();
                }
            }
        } catch (InterruptedException err){
            err.printStackTrace();
        }
    }

    public void printB(){
        try{
            synchronized (mon){
                for (int i=0; i<10; i++){
                    while (currentLetter != 'B'){
                        mon.wait();
                    }
                    System.out.println("B");
                    currentLetter = 'C';
                    mon.notifyAll();
                }
            }

        }catch(InterruptedException err){
            err.printStackTrace();
        }
    }

    public void printC(){
        try{
            synchronized (mon){
                for (int i=0; i<10; i++){
                    while (currentLetter != 'C'){
                        mon.wait();
                    }
                    System.out.println("C");
                    currentLetter = 'A';
                    mon.notifyAll();
                }
            }

        }catch(InterruptedException err){
            err.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ABCThread printABC = new ABCThread();
        Thread threadA = new Thread(() -> {
            printABC.printA();
        });

        Thread threadB = new Thread(() -> {
            printABC.printB();
        });

        Thread threadC = new Thread(() -> {
            printABC.printC();
        });
        threadA.start();
        threadB.start();
        threadC.start();

    }


}
