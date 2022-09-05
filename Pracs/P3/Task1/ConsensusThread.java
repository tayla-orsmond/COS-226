//Tayla Orsmond u21467456
public class ConsensusThread extends Thread
{
	private Consensus<Integer> consensus;
	private int ID, max = 200, min = 100;
	private Integer proposedValue, decidedValue;
	Thread t;
	public ConsensusThread(Consensus<Integer> consensusObject, int id){
		consensus = consensusObject;
		ID = id;
	}

	@Override
	public void run()
	{
		for(int i = 0; i < 5; i++){
			//propose a value
			proposedValue = (int)(Math.random() * (max - min) + min);
			System.out.println("Thread " + ID + " proposed " + proposedValue);
			consensus.propose(proposedValue);
			//wait for the value to be decided
			try{
				Thread.sleep((long)(Math.random() * (100 - 50) + 50));
			}
			catch(InterruptedException e){}
			
			consensus.decide();
			
			//sleep again
			try{
				Thread.sleep((long)(Math.random() * (100 - 50) + 50));
			}
			catch(InterruptedException e){}
		}
	}

	@Override
	public void start(){
        if(t == null){
            t = new Thread(this, String.valueOf(ID));
            t.start();
        }
    }
}
