public class Main {
    public static void main(String[] args) {
        int numThreads = 10;
        int numEnqers = 6;
        int numOps = 5;
        int maxSleep = 1000;
        int capacity = 5;
        BoundedQueue<String> queue = new BoundedQueue<String>(capacity, numEnqers * numOps);
        QThread[] threads = new QThread[numThreads];
        for(int i = 0; i < numEnqers; i++){
            threads[i] = new QThread(queue, i, true, numOps, maxSleep);
        }
        for(int i = numEnqers; i < numThreads; i++){
            threads[i] = new QThread(queue, i, false, numOps, maxSleep);
        }
        for(int i = 0; i < numThreads; i++){
            threads[i].start();
        }
        for(int i = 0; i < numThreads; i++){
            try{
                threads[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Final queue: " + queue.toString());
    }
}
