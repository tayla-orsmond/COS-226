//Tayla Orsmond u21467456
public interface Consensus<T>
{
	void decide();
	void propose(T value);
}
