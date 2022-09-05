import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
//Tayla Orsmond u21467456

public class Project
{
	private Bakery devlock, testlock;
	private volatile Queue<Component> develop = new LinkedList<>(), testing = new LinkedList<>();
	private int max = 500, min = 100, breakmax = 100, breakmin = 50;
	public volatile int toDevelop, tested;
	
	public Project(int max){
		devlock = new Bakery(max);
		testlock = new Bakery(max);
		develop.add(new Component('s', "Calculator"));
		develop.add(new Component('m', "Calendar"));
		develop.add(new Component('s', "Billing"));
		develop.add(new Component('l', "Timetable"));
		develop.add(new Component('m', "Phonebook"));
		develop.add(new Component('l', "User Manager"));
		develop.add(new Component('s', "Api"));
		toDevelop = develop.size();
		tested = 0;
	}
	public Queue<Component> getDevs(){
		return develop;
	}
	public Queue<Component> getTests(){
		return testing;
	}
	public void develop(){
		System.out.println("Dev Thread: "+ Integer.parseInt(Thread.currentThread().getName()) +" is ready to develop a component.");
		devlock.lock();//lock
		long time = (long)(Math.random() * (max - min)) + min;
		long breakTime = (long)(Math.random() * (breakmax - breakmin)) + breakmin;
		try{//develop
			Component component = develop.remove(); //remove from queue
			component.developTime -= time;
			if(component.developTime <= 0){
				System.out.println("Dev Thread: "+ Integer.parseInt(Thread.currentThread().getName()) +" finished developing " + component.name);
				//component = develop.poll();
				testing.add(component);//add to testing
			}
			else{
				System.out.println("Dev Thread: "+ Integer.parseInt(Thread.currentThread().getName()) +" developed " + component.name +" for "+ time +"ms. Time remaining: "+
				component.developTime +"ms");
				develop.add(component);//add back to queue
			}
			Thread.currentThread().sleep(time);

		}catch(InterruptedException e){}catch(NoSuchElementException x){}
		devlock.unlock();
		try{//rest

			System.out.println("Dev Thread: "+ Integer.parseInt(Thread.currentThread().getName()) + " is taking a break.");
			Thread.currentThread().sleep(breakTime);

		}catch(InterruptedException x){}
	}

	public void test(){
		System.out.println("Tester Thread: "+ Integer.parseInt(Thread.currentThread().getName()) +" is ready to test a component.");
		testlock.lock();
		long time = (long)(Math.random() * (max - min)) + min;
		long breakTime = (long)(Math.random() * (breakmax - breakmin)) + breakmin;
		try{//test
			Component component = testing.remove();//remove from queue;
			component.testTime -= time;
			if(component.testTime <= 0){
				System.out.println("Tester Thread: "+ Integer.parseInt(Thread.currentThread().getName()) +" finished testing " + component.name);
				//testing.poll();
				tested++;
			}
			else{
				System.out.println("Tester Thread: "+ Integer.parseInt(Thread.currentThread().getName()) +" tested " + component.name +" for "+ time +"ms. Time remaining: "+
				component.testTime +"ms");
				testing.add(component);//add back to queue
			}
			Thread.currentThread().sleep(time);

		}catch(InterruptedException e){}catch(NoSuchElementException x){}
		testlock.unlock();
		try{//rest

			System.out.println("Tester Thread: "+ Integer.parseInt(Thread.currentThread().getName()) + " is taking a break.");
			Thread.currentThread().sleep(breakTime);

		}catch(InterruptedException x){}
	}
}
