public class Main {
    public static void main(String args[]){
        int CAP = 5;
        Timeout queue = new Timeout();
        VotingStation vs = new VotingStation(CAP, queue);
        Marshal threads [] = new Marshal[CAP];

        System.out.println("Capacity: " + CAP + "\n");

        for(int i = 0; i < CAP; i++){
            threads[i] = new Marshal(vs, CAP, i);
        }
        for(int i = 0; i < CAP; i++){
            threads[i].start();
        }
    }
}
