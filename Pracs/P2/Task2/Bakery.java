import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Tayla Orsmond
// Student Number: u21467456

public class Bakery implements Lock {
	boolean[] flag;
	int[] label;
	private int n;

	public Bakery(int max){
		n = max;
		flag = new boolean[n];
		label = new int[n];
		for (int i = 0; i < n; i++){
			flag[i] = false; 
			label[i] = 0;
		}
	}
	public void lock() {
		flag[Integer.parseInt(Thread.currentThread().getName())] = true; //I'm interested
		label[Integer.parseInt(Thread.currentThread().getName())] = maximum() +1; 
			//Take increasing label (read labels in some arbitrary order)
			//Label is created as one greater than the maximum 
			//of the other thread’s labels
		for(int k = 0; k < n; k++){
			while(k != Integer.parseInt(Thread.currentThread().getName()) && flag[k] && label[k] < label[Integer.parseInt(Thread.currentThread().getName())] || (label[k] == label[Integer.parseInt(Thread.currentThread().getName())] && k < Integer.parseInt(Thread.currentThread().getName()))){
				//Someone is interested
				//With lower (label, i) in lexicographic order
			}
		}
	}
	public void unlock() {
		flag[Integer.parseInt(Thread.currentThread().getName())] = false; //No longer interested
	}
	
	public int maximum(){
		int max = 0;
		for (int i = 0; i < n; i++){
			if(label[i] > max){
				max = label[i];
			}
		}
		return max;
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
