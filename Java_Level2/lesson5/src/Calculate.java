public class Calculate {
    static final int SIZE = 10000000;
    static final int HALF = SIZE/2;


    float[] singleRun(){
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        changeValues(arr);
        long end = System.currentTimeMillis();
        System.out.println("Execution time with 1 thread - " + (end -start));
        return arr;
    }

    float[] doubleRun() throws InterruptedException {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        float[] first  = new float[HALF];
        float[] second = new float[HALF];

        long start = System.currentTimeMillis();
        System.arraycopy(arr,0, first, 0, HALF);
        System.arraycopy(arr, HALF, second, 0, HALF);

        Thread fThread = new Thread(()->{
            changeValues(first);
        });

        Thread sThread = new Thread(()->{
            changeValues(second);
        });
        fThread.start();
        sThread.start();
        fThread.join();
        sThread.join();

        System.arraycopy(first, 0, arr, 0, HALF);
        System.arraycopy(second, 0, arr, HALF, HALF);

        long end = System.currentTimeMillis();
        System.out.println("Execution time for with 2 threads - " + (end - start));
        return arr;
    }

    private void changeValues(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

    }

    public static void main(String[] args) throws InterruptedException{
        Calculate calculate = new Calculate();
        calculate.singleRun();
        calculate.doubleRun();

    }
}
