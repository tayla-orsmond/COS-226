import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class Main {
    public static void main(String[] args) {
        int maxAccesses = 1;
        int minAccesses = 1;
        int[] threadCounts = {1, 2, 4, 8, 10, 16, 32};
        long[][] times = new long[3][threadCounts.length];
        TASLock TAS = new TASLock();
        TTASLock TTAS = new TTASLock();
        Backoff backoff = new Backoff();
        Lock locks[] = {TAS, TTAS, backoff};
        System.out.println("Number of Threads: "+ Arrays.toString(threadCounts));
        System.out.println("---------------------------------------------");
        for(int j = 0; j < threadCounts.length; j++){
            LockThread[] threads = new LockThread[threadCounts[j]];
            for(int k = 0; k < threadCounts[j]; k++){
                threads[k] = new LockThread(TAS, TTAS, backoff, k, maxAccesses, minAccesses);
            }
            for(int k = 0; k < threadCounts[j]; k++){
                threads[k].start();
            }
            for(int k = 0; k < threadCounts[j]; k++){
                try {
                    threads[k].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int k = 0; k < threadCounts[j]; k++){
                times[0][j] += threads[k].TASTime;
                times[1][j] += threads[k].TTASTime;
                times[2][j] += threads[k].BackoffTime;
            }
        }
        for(int i = 0; i < locks.length; i++){
            System.out.print(locks[i].getClass().getName() + ": ");
            System.out.println(Arrays.toString(times[i]));
        }
        System.out.println("---------------------------------------------");
    }
}
