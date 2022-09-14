import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u2147456
public class MCSQueue implements Lock{
    private AtomicReference<Qnode> tail;
    private ThreadLocal<Qnode> myNode;
    public MCSQueue() {
        tail = new AtomicReference<Qnode>(null);
        myNode = new ThreadLocal<Qnode>() {
            protected Qnode initialValue() {
                return new Qnode();
            }
        };
    }
    @Override
    public void lock() {
        Qnode node = myNode.get();
        Qnode pred = tail.getAndSet(node);
		System.out.println("Thread-"+ Thread.currentThread().getName() + " with Person-" + ((Marshal) Thread.currentThread()).getNo() + " entered the voting station.");
        if(pred != null) {
            node.setLocked(true);
            pred.setNext(node);
            node.setPrev(pred);
            while (node.isLocked()) {
                // spin
            }
        }
    }

    @Override
    public void unlock() {
        printQueue();
        Qnode node = myNode.get();
        if (node.getNext() == null) {
            if (tail.compareAndSet(node, null)) {
                return;
            }
            while (node.getNext() == null) {
                // spin
            }
        }
        node.getNext().setPrev(null);
        node.getNext().setLocked(false);
        node.setNext(null);
    }
    public void printQueue(){
        Qnode node = tail.get();
        System.out.print("Queue: ");
        while(node != null){
            System.out.print("{Thread-"+ node.parent.getName() + " with Person-" + node.parent.getNo() + "}");
            if(node.getPrev() != null){
                System.out.print("-> ");
            }
            else{
                System.out.println(" [Head]");
            }
            node = node.getPrev();
        }
        System.out.println();
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

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
