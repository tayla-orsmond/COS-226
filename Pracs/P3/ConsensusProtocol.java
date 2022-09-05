//Tayla Orsmond u21467456
public abstract class ConsensusProtocol<T> implements Consensus<T>{
	public volatile Object[] proposed;

	public ConsensusProtocol(int threadCount)	{
		proposed = new Object[threadCount];
	}

	public void propose(T value){
		proposed[Integer.parseInt(Thread.currentThread().getName())] = value;
		System.out.println("Thread " + Thread.currentThread().getName() + " proposed " + value);
	}

	abstract public void decide();
}
