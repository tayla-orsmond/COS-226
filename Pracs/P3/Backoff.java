//Tayla Orsmond u21467456
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
public class Backoff implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);
    private int minDelay = 100, maxDelay = 200;
    @Override
    public void lock() {
        int delay = minDelay; //Fix minimum delay
        while (true) {
            while (state.get()) {}//Wait until lock looks free
            if (!state.getAndSet(true)){
                return;//If we win, return
            }
            try{
                Thread.sleep((long)Math.random() % delay);//Otherwise back off for random duration
            }catch(InterruptedException e){}
            if (delay < maxDelay){//Increase delay
                delay = 2 * delay;//Double max delay, within reason
            }
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