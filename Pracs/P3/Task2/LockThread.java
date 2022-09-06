//Tayla Orsmond u21467456
import java.util.concurrent.locks.Lock;
public class LockThread extends Thread
{
    private TASLock TAS;
    private TTASLock TTAS;
    private Backoff backoff;
	private int maxAccesses;
    public long TASTime;
    public long TTASTime;
    public long BackoffTime;
	Thread t;
	public LockThread(TASLock TAS, TTASLock TTAS, Backoff backoff, int id, int max, int min){
        this.TAS = TAS;
        this.TTAS = TTAS;
        this.backoff = backoff;
        this.setName(String.valueOf(id));
        maxAccesses = (int)(Math.random() * (max - min + 1) + min);
	}

	@Override
	public void run()
	{
        //access CS a variable no. of times
		for(int i = 0; i < maxAccesses; i++){
            final long startTime = System.currentTimeMillis();
            TAS.lock();
            try {
                Thread.sleep(100);//sleep for 100ms
            } catch (InterruptedException e) {}
            TAS.unlock();
            final long endTime = System.currentTimeMillis();
            TASTime += endTime - startTime;
        }
        for(int i = 0; i < maxAccesses; i++){
            final long startTime = System.currentTimeMillis();
            TTAS.lock();
            try {
                Thread.sleep(100);//sleep for 100ms
            } catch (InterruptedException e) {}
            TTAS.unlock();
            final long endTime = System.currentTimeMillis();
            TTASTime += endTime - startTime;
        }
        for(int i = 0; i < maxAccesses; i++){
            final long startTime = System.currentTimeMillis();
            backoff.lock();
            try {
                Thread.sleep(100);//sleep for 100ms
            } catch (InterruptedException e) {}
            backoff.unlock();
            final long endTime = System.currentTimeMillis();
            BackoffTime += endTime - startTime;
        }
	}
}
