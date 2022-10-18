import java.util.concurrent.atomic.AtomicInteger;

//Tayla Orsmond u21467456
// Bounded Lock-based Queue

public class BoundedLockFreeQueue<T>{
    private AtomicInteger size = new AtomicInteger(0);
    private int capacity;
    private AtomicInteger head;
    private AtomicInteger tail;
    private T[] array;
    public static volatile int maxEnq;

    @SuppressWarnings("unchecked")
    public BoundedLockFreeQueue(int capacity, int maxEnq){
        this.capacity = capacity;
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
        this.array = (T[]) new Object[capacity];
        BoundedLockFreeQueue.maxEnq = maxEnq;
    }

    public void enq(T item) throws Exception{
        while(true){
            try{
                if(size.get() == capacity){
                    throw new Exception("Enq Failed: Full Queue");
                }
                Integer last = tail.get();
                if(last == tail.get() && array[last] == null){
                    array[last] = item;
                    tail.set((last + 1) % capacity);
                    size.incrementAndGet();
                    BoundedLockFreeQueue.maxEnq--;
                    System.out.println("Thread-" + Thread.currentThread().getName() + " enqueued " + item);
                    return;
                }   
            } catch(Exception e){
                // System.out.println(e.getMessage());
            }
        }
    }

    public T deq() throws Exception{
        T item = null;
        while(true){
            try{
                if(size.get() == 0){
                    throw new Exception("Deq Failed: Empty Queue");
                }
                Integer first = head.get();
                if(first == head.get() && array[first] != null){
                    item = array[first];
                    array[first] = null;
                    head.set((head.get() + 1) % capacity);
                    size.decrementAndGet();
                    System.out.println("\tThread-" + Thread.currentThread().getName() + " dequeued " + item + " QUEUE: " + this.toString());
                    return item;
                }
            } catch(Exception e){
                // System.out.println(e.getMessage());
            }
        }
    }

    public int size(){
        return size.get();
    }

    public String toString(){
        return java.util.Arrays.toString(array);
    }
}