import java.util.concurrent.TimeUnit;
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
	public void castBallot(int i){
		while(voters.get() >= capacity){}//wait outside 
		if(voters.get() < capacity){
			voters.incrementAndGet();
			boolean l = false;
			while(!l){
				l = lock.tryLock();
			}
			try{
				Thread.sleep((long)(Math.random() * (maxTimeout - minTimeout) + minTimeout));
				System.out.println("\nThread-"+ Thread.currentThread().getName() + " with Person-" + i + " cast a vote.");
			}
			catch(InterruptedException e){}
			finally{
				lock.unlock();
				voters.decrementAndGet();
			}
		}
	}
}
