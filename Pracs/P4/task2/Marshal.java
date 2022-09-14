//Tayla Orsmond u2147456
public class Marshal extends Thread {

    private VotingStation vs;
	private int numVoters, person;
	Marshal(VotingStation vs, int numVoters, int ID)
	{
		this.vs = vs;
		this.numVoters = numVoters;
		this.setName(String.valueOf(ID));
	}

	@Override
	public void run()
	{
		for(int i = 0; i < numVoters; i++)
		{
			person = i;
			vs.castBallot(i);
		}
	}
	public int getNo(){
		return person;
	}
}
