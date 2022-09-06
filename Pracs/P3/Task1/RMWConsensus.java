//Tayla Orsmond u21467456
public class RMWConsensus<T> extends ConsensusProtocol<T>{
    public RMWConsensus(int threadCount) {
        super(threadCount);
    }
    public void decide(){
        System.out.print("\t\t\t[Proposed values: ");
        for(int i = 0; i < proposed.length; i++){
            System.out.print("Thread " + i + ": " + proposed[i] + " ");
        }
        System.out.println("]");
        if (proposed[Integer.parseInt(Thread.currentThread().getName())] != proposed[1 - Integer.parseInt(Thread.currentThread().getName())]){//Am I first?
		    proposed[1 - Integer.parseInt(Thread.currentThread().getName())] = proposed[Integer.parseInt(Thread.currentThread().getName())]; //Yes, return my input
            System.out.println("\t\t\tThread " + Thread.currentThread().getName() + " is first!");
        }
            //else I lost (am second), return the other thread's input (which is now mine too)
        System.out.println("Thread " + Thread.currentThread().getName() + " decided " + proposed[0]);
        System.out.println();
    }
}
