import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u21467456
public class Peterson implements Lock{
    private int id;
    private int jd;
    private int victim;
    private boolean [] flag = new boolean[2];

    @Override
    public void lock() {
        id = Integer.parseInt(Thread.currentThread().getName());
        jd = 1 - id;
        flag[id] = true; // announce I'm interested
        victim = id; // defer to other thread
        while (flag[jd] && victim == id){
            // wait while I'm interested && the victim
        };
        //System.out.println("Thread-"+id+" is locking.");
    }

    @Override
    public void unlock() {
        id = Integer.parseInt(Thread.currentThread().getName());
        jd = 1 - id;
        flag[id] = false; // no longer interested
        //System.out.println("Thread-"+id+" is unlocking.");
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
