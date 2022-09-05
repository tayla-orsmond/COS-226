public class Main {
    public static void main(String[] args) {
        int MAX = 2;
        ConsensusThread threads [] = new ConsensusThread[2];
        RMWConsensus<Integer> consensus = new RMWConsensus<Integer>(2);
        for (int i = 0; i < MAX; i++) {
            threads[i] = new ConsensusThread(consensus, i);
        }
        for (int i = 0; i < MAX; i++) {
            threads[i].start();
        }
    }
}
