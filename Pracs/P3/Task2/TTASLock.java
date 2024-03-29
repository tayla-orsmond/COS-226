//Tayla Orsmond u21467456
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
public class TTASLock implements Lock{
    AtomicBoolean state = new AtomicBoolean(false);
    @Override
    public void lock() {
        while (true) {
            while (state.get()) {}//Wait until lock looks free
            if (!state.getAndSet(true))
                return;//Then try to acquire it
        }
    }
    @Override
    public void unlock() {
        state.set(false);
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