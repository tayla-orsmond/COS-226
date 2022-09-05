public class Transport extends Thread {
	
// Name: Tayla Orsmond
// Student Number: u21467456
    Venue destination;
	private int ID, load = 0;
	Thread t;
	public Transport(Venue dest, int id){
		destination = dest;
		ID = id;
        //System.out.println("Creating thread: " + ID);
	}

	@Override
	public void run()
	{
		for(int i = 0; i < 5; i++){
			load = i+1;
			destination.dropOff(load);
		}
	}
	@Override
	public void start(){
        //System.out.println("Starting: " + ID);
        if(t == null){
            t = new Thread(this, String.valueOf(ID));
            t.start();
        }
    }
	public int getLoad(){
		return load;
	}
}
