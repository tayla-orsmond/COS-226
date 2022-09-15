import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u2147456
public class Timeout implements Lock {
    AtomicReference<Qnode> tail;
    ThreadLocal<Qnode> myNode;
    static Qnode AVAILABLE = new Qnode(null);
	long maxTimeout = 1000, minTimeout = 200;
    public Timeout() {
        tail = new AtomicReference<Qnode>(null);
        myNode = new ThreadLocal<Qnode>() {
            protected Qnode initialValue() {
                return new Qnode();
            }
        };
    }
    @Override
    public boolean tryLock() {
        Qnode node = new Qnode();
        myNode.set(node);
        Qnode next = tail.getAndSet(node);
        System.out.println("Thread-"+ Thread.currentThread().getName() + " with Person-" + ((Marshal) Thread.currentThread()).getNo() + " entered the voting station.");
        if(next == null || next.getNext() == AVAILABLE){
            AVAILABLE.setPrev(node);
            node.setPrev(next);
            return true;
        }
        long timeout = (long)(Math.random() * (maxTimeout - minTimeout) + minTimeout);
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() - start < timeout){
            Qnode nextNext = next.getNext();
            if(nextNext == AVAILABLE){
                AVAILABLE.setPrev(node);
                node.setPrev(next);
                return true;
            }
            else if(nextNext != null){
                next = nextNext;
            }
            next.setPrev(node);
        }
        if(!tail.compareAndSet(node, next)){
            next.setPrev(node);
            node.setNext(next);
        }
        System.out.println("\t\t Thread-"+ Thread.currentThread().getName() + " with Person-" + ((Marshal) Thread.currentThread()).getNo() + " timed out.");
        return false;
    }
    @Override
    public void unlock() {
        printQueue();
        Qnode node = myNode.get();
        if(!tail.compareAndSet(node, null)){
            node.setNext(AVAILABLE);
        }
    }
    public void printQueue(){
        Qnode node = AVAILABLE.getPrev();
        System.out.print("Queue: ");
        while(node != null && node != tail.get()){
            System.out.print("{Thread-"+ node.parent.getName() + " with Person-" + node.parent.getNo() + "}");
            if(node.getPrev() != null && node != tail.get()){
                System.out.print("-> ");
            }
            else{
                System.out.println(" [Tail]");
            }
            node = node.getPrev();
        }
        if(node == tail.get()){
            System.out.print("{Thread-"+ node.parent.getName() + " with Person-" + node.parent.getNo() + "} [Tail]");
        }
        System.out.println();
    }
    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void lock() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }
}
