import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
//Tayla Orsmond u2147456
public class VotingStation {
    Lock lock;
	AtomicInteger voters;
	final int capacity;
	long maxTimeout = 1000, minTimeout = 200;

	VotingStation(int capacity, Lock lock)
	{
		this.capacity = capacity;
		this.lock = lock;
		voters = new AtomicInteger(0);
	}
	public void castBallot(){
		if(voters.get() < capacity){
			voters.incrementAndGet();
			lock.lock();
			try{
				Thread.sleep((long)(Math.random() * (maxTimeout - minTimeout) + minTimeout));
			}
			catch(InterruptedException e){}
			finally{
				lock.unlock();
				voters.decrementAndGet();
			}
		}
	}
}
