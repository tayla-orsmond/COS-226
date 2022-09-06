import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class Main {
    public static void main(String[] args) {
        int maxAccesses = 4;
        int minAccesses = 1;
        int[] threadCounts = {1, 2, 4, 8, 10, 16};
        Lock[] locks = new Lock[3];
        locks[0] = new TASLock();
        locks[1] = new TTASLock();
        locks[2] = new Backoff();
        
        System.out.println("Number of Threads: "+ Arrays.toString(threadCounts));
        System.out.println("---------------------------------------------");
        for(int i = 0; i < locks.length; i++){
            System.out.print(locks[i].getClass().getName() + ": ");
            System.out.print("[");
            for(int j = 0; j < threadCounts.length; j++){
                LockThread[] threads = new LockThread[threadCounts[j]];
                long totalElapsedTime = 0;
                LockThread.resetElapsedTime();
                for(int k = 0; k < threadCounts[j]; k++){
                    threads[k] = new LockThread(locks[i], k, maxAccesses, minAccesses);
                    threads[k].run();
                    try {
                        threads[k].join();
                    } catch (InterruptedException e) {
                    }
                }
                totalElapsedTime = LockThread.getElapsedTime();
                System.out.print(totalElapsedTime);
                if(j != threadCounts.length - 1){
                    System.out.print(", ");
                }
            }
            System.out.print("] time in ms");
            System.out.println();
        }
        System.out.println("---------------------------------------------");
    }
}
