import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u2147456
public class Timeout implements Lock {
    private AtomicReference<Qnode> tail;
    private ThreadLocal<Qnode> myNode;
    static Qnode AVAILABLE;
    public Timeout() {
        tail = new AtomicReference<Qnode>(null);
        myNode = new ThreadLocal<Qnode>() {
            protected Qnode initialValue() {
                return new Qnode();
            }
        };
    }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long start = System.currentTimeMillis();
        long timeout = unit.toMillis(time);
        Qnode node = myNode.get();
        Qnode next = tail.getAndSet(node);
        System.out.println("Tail: Thread-"+ tail.get().parent.getName());
        if(next == null || next.getNext() == AVAILABLE){
		    System.out.println("Thread-"+ Thread.currentThread().getName() + " with Person-" + ((Marshal) Thread.currentThread()).getNo() + " entered the voting station.");
            return true;
        }
        while(System.currentTimeMillis() - start < timeout){
            Qnode nextNext = next.getNext();
            if(nextNext == AVAILABLE){
		        System.out.println("Thread-"+ Thread.currentThread().getName() + " with Person-" + ((Marshal) Thread.currentThread()).getNo() + " entered the voting station.");
                return true;
            }
            if(nextNext != null){
                next = nextNext;
            }
        }
        if(!tail.compareAndSet(node, next)){
            node.setNext(next);
        }
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
        Qnode node = tail.get();
        System.out.print("Queue: ");
        while(node != null){
            System.out.print("{Thread-"+ node.parent.getName() + " with Person-" + node.parent.getNo() + "}");
            if(node.getNext() != null || node.getNext() != AVAILABLE){
                System.out.print("-> ");
            }
            else{
                System.out.println(" [Head]");
            }
            node = node.getNext();
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
    public boolean tryLock() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
