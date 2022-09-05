import java.util.concurrent.locks.Lock;

// Name: Tayla Orsmond
// Student Number: u21467456

public class Venue {
    Lock l;
	private int max = 1000;
	private int min = 200;

	public Venue(int num) {
		//l = new Filter(num);
		l = new Bakery(num);
	}
	
	public void dropOff(int load){
		System.out.println("Bus " + Thread.currentThread().getName() + " is waiting to drop off load: "+ load);
		//lock
		l.lock();
		//sleep
		try{
			Thread.currentThread().sleep((long)(Math.random() * (max - min)) + min);
		}catch(InterruptedException e){}
		System.out.println("Bus " + Thread.currentThread().getName() + " is dropping-off load: "+ load);
		//unlock
		l.unlock();
		System.out.println("Bus " + Thread.currentThread().getName() + " has left load: "+ load);
	}
}
