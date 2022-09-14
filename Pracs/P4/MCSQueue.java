import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u2147456
public class MCSQueue implements Lock{
    private AtomicReference<MCSNode> tail;
    private ThreadLocal<MCSNode> myNode;
    public MCSQueue() {
        tail = new AtomicReference<MCSNode>(null);
        myNode = new ThreadLocal<MCSNode>() {
            protected MCSNode initialValue() {
                return new MCSNode();
            }
        };
    }
    @Override
    public void lock() {
        MCSNode node = myNode.get();
        MCSNode pred = tail.getAndSet(node);
        if (pred != null) {
            node.setLocked(true);
            pred.setNext(node);
            while (node.isLocked()) {
                // spin
            }
        }
        
    }

    @Override
    public void unlock() {
        MCSNode node = myNode.get();
        if (node.getNext() == null) {
            if (tail.compareAndSet(node, null)) {
                return;
            }
            while (node.getNext() == null) {
                // spin
            }
        }
        node.getNext().setLocked(false);
        node.setNext(null);
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
