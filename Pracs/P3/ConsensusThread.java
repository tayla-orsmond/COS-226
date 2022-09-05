//Tayla Orsmond u21467456
public class ConsensusThread extends Thread
{
	private Consensus<Integer> consensus;
	private int ID, max = 200, min = 100;
	private Integer proposedValue;
	Thread t;
	public ConsensusThread(Consensus<Integer> consensusObject, int id){
		consensus = consensusObject;
		ID = id;
	}

	@Override
	public void run()
	{
		//propose a value
		proposedValue = (int)Math.random() * (max - min)) + min;
		consensus.propose(proposedValue);
	}

	@Override
	public void start(){
        if(t == null){
            t = new Thread(this, String.valueOf(ID));
            t.start();
        }
    }
}
