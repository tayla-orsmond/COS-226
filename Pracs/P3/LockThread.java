//Tayla Orsmond u21467456
import java.util.concurrent.locks.Lock;
public class LockThread extends Thread
{
    private Lock l;
	private int maxAccesses;
    private static long elapsedTime;
	Thread t;
	public LockThread(Lock lock, int id, int max, int min){
        l = lock;
        this.setName(String.valueOf(id));
        maxAccesses = (int)(Math.random() * (max - min + 1) + min);
	}

	@Override
	public void run()
	{
        //access CS a variable no. of times
		for(int i = 0; i < maxAccesses; i++){
            final long startTime = System.currentTimeMillis();
            l.lock();
            try {
                Thread.sleep(100);//sleep for 100ms
            } catch (InterruptedException e) {}
            l.unlock();
            final long endTime = System.currentTimeMillis();
            setElapsedTime(endTime - startTime);
        }
	}
    public static void setElapsedTime(long time){
        elapsedTime += time;
    }
    public static long getElapsedTime() {
        return elapsedTime;
    }

    public static void resetElapsedTime() {
        elapsedTime = 0;
    }
}
