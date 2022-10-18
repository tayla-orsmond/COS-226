import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//Tayla Orsmond u21467456
// Bounded Lock-based Queue

public class BoundedQueue<T>{
    ReentrantLock enqLock = new ReentrantLock();
    ReentrantLock deqLock = new ReentrantLock();
    Condition notFull = enqLock.newCondition();
    Condition notEmpty = deqLock.newCondition();
    private AtomicInteger size = new AtomicInteger(0);
    private int capacity;
    private AtomicInteger head;
    private AtomicInteger tail;
    private T[] array;
    public static int maxEnq;

    @SuppressWarnings("unchecked")
    public BoundedQueue(int capacity, int maxEnq){
        this.capacity = capacity;
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
        this.array = (T[]) new Object[capacity];
        BoundedQueue.maxEnq = maxEnq;
    }

    public void enq(T item) throws InterruptedException{
        boolean wakeywakey = false;
        enqLock.lock();
        try{
            boolean timeout = true;
            while(size.get() == capacity && timeout){
                timeout = notFull.await(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
            }
            array[tail.get()] = item;
            tail.set((tail.get() + 1) % capacity);
            BoundedQueue.maxEnq--;
            System.out.println("\t\tThread-" + Thread.currentThread().getName() + " enqueued " + item);
            if(size.getAndIncrement() == 0){
                wakeywakey = true;
            }
        }finally{
            enqLock.unlock();
        }
        if(wakeywakey){
            deqLock.lock();
            try{
                notEmpty.signalAll();
            }finally{
                deqLock.unlock();
            }
        }
    }

    public T deq() throws InterruptedException{
        T item = null;
        boolean wakeywakey = false;
        deqLock.lock();
        try{
            boolean timeout = true;
            while(size.get() == 0 && timeout){
                timeout = notEmpty.await(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
            }
            Integer index = head.get();
            item = array[index];
            array[index] = null;
            head.set((head.get() + 1) % capacity);
            System.out.println("Thread-" + Thread.currentThread().getName() + " dequeued " + item + " QUEUE: " + this.toString());
            if(size.getAndDecrement() == capacity){
                wakeywakey = true;
            }
        }finally{
            deqLock.unlock();
        }
        if(wakeywakey){
            enqLock.lock();
            try{
                notFull.signalAll();
            }finally{
                enqLock.unlock();
            }
        }
        return item;
    }

    public int size(){
        return size.get();
    }

    public String toString(){
        return java.util.Arrays.toString(array);
    }
}