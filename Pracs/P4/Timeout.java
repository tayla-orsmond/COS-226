import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u2147456
public class Timeout implements Lock {
    private AtomicReference<MCSNode> tail;
    private ThreadLocal<MCSNode> myNode;
    static MCSNode AVAILABLE;
    public Timeout() {
        tail = new AtomicReference<MCSNode>(null);
        myNode = new ThreadLocal<MCSNode>() {
            protected MCSNode initialValue() {
                return new MCSNode();
            }
        };
    }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long start = System.currentTimeMillis();
        long timeout = unit.toMillis(time);
        MCSNode node = myNode.get();
        MCSNode next = tail.getAndSet(node);
        if(next == null || next.getNext() == AVAILABLE){
            return true;
        }
        while(System.currentTimeMillis() - start < timeout){
            MCSNode nextNext = next.getNext();
            if(nextNext == AVAILABLE){
                return true;
            }
            else if(nextNext != null){
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
        MCSNode node = myNode.get();
        if(!tail.compareAndSet(node, null)){
            node.setNext(AVAILABLE);
        }
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
