import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Tayla Orsmond
// Student Number: u21467456

public class Filter implements Lock
{
	//[I am thread i]
	private int[] level; //level[i] for thread i
	private int[] victim; //victim[L] for level L
	private int n;

	public Filter(int max) {
		n = max;
		level = new int[n];
		victim = new int[n];
		for(int i = 0; i < n; i++){
			level[i] = 0;
		}
	}
	public void lock(){
		for(int L = 1; L < n; L++){ //One level at a time
			level[Integer.parseInt(Thread.currentThread().getName())] = L; //Announce intention to enter level L
			victim[L] = Integer.parseInt(Thread.currentThread().getName()); //Give priority to anyone but me
			for(int k = 0; k < n; k++){
				while(k != Integer.parseInt(Thread.currentThread().getName()) && level[k] >= L && victim[L] == Integer.parseInt(Thread.currentThread().getName()))
				{
					//Wait as long as someone else is at same or higher level, 
					//and I’m designated victim 
				}
			}
			//Thread enters level L when it completes the loop
			//Thread i, in the critical section, releses the lock and that
			//facilitates movement of threads behind i
		}
	}
	public void unlock(){
		level[Integer.parseInt(Thread.currentThread().getName())] = 0;
	}

	public void lockInterruptibly() throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}

	public boolean tryLock()
	{
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}

	public Condition newCondition()
	{
		throw new UnsupportedOperationException();
	}

}
