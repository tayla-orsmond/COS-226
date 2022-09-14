//Tayla Orsmond u2147456
public class Marshal extends Thread {

    private VotingStation vs;
	private int numVoters;
	
	Marshal(VotingStation vs, int numVoters)
	{
		this.vs = vs;
		this.numVoters = numVoters;
	}

	@Override
	public void run()
	{
		for(int i = 0; i < numVoters; i++)
		{
			vs.castBallot();
		}
	}
}
